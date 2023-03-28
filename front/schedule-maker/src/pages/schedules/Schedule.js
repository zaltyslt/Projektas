import React, { useState, useEffect } from "react";
import "./AppScheduleView.css";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import listPlugin from "@fullcalendar/list";
import allLocales from "@fullcalendar/core/locales-all";
import FullCalendar from "@fullcalendar/react";
import { Link, useParams } from "react-router-dom";
import "./ViewSchedule.css";
import { Stack } from "@mui/system";
import { Button, Grid } from "@mui/material";
import adaptivePlugin from "@fullcalendar/adaptive";
import { render } from "preact/compat";

import Dialog from '@mui/material/Dialog';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import DialogActions from '@mui/material/DialogActions';
import CloseIcon from '@mui/icons-material/Close';
import { useTheme } from '@mui/material/styles';
import useMediaQuery from '@mui/material/useMediaQuery';
import DialogContentText from '@mui/material/DialogContentText';

export function Schedule() {
  const [weekendsVisible, setWeekendsVisible] = useState(true);
  const [schedule, setSchedule] = useState([]);
  const [holiday, setHoliday] = useState([]);
  const params = useParams();

  const [open, setOpen] = React.useState(false);
  const theme = useTheme();
  const fullScreen = useMediaQuery(theme.breakpoints.down('md'));
  const [maxWidth, setMaxWidth] = React.useState('sm');
  const [fullWidth, setFullWidth] = React.useState(true);

  const [conflictDates, setConflictDates] = useState([])

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const subjectColors = [
    "#f5c5c4",
    "#f5ddc4",
    "#f5f3c4",
    "#daf5c4",
    "#c4f5d8",
    "#c4f5f2",
    "#c4d3f5",
    "#d8c4f5",
    "#f5c4e3",
  ];

  useEffect(() => {
    fetch(`api/v1/schedules/${params.id}/lessons`)
      .then((response) => response.json())
      .then((data) => setSchedule(data))
      .catch((error) => console.error(error));
  }, [params.id]);

  useEffect(() => {
    fetch(`api/v1/schedules/holidays/${params.id}`)
      .then((response) => response.json())
      .then((data) => setHoliday(data))
      .catch((error) => console.error(error));
  }, [params.id]);

  const events = [
    ...schedule.map((schedule) => {
      const color = subjectColors[schedule.subject.id];
      return {
        title: `<b>${schedule.subject.name}</b>
          <br />
          ${schedule.lessonStart} - ${schedule.lessonEnd}
          <br />
          ${schedule.teacher ? schedule.teacher.lName : ""} ${schedule.teacher ? schedule.teacher.fName : "nepasirinktas"
          }
          <br />
          ${schedule.online
            ? "Nuotolinė pamoka"
            : schedule.classroom.classroomName
          }<br />
          `,
        start: schedule.date,
        allDay: true,
        url: `http://localhost:3000/schedule-maker#/schedules/edit-lesson/${schedule.id}`,
        color: color,
      };
    }),
    ...holiday.map((holiday) => ({
      title: `<b>${holiday.name}</b>`,
      start: holiday.dateFrom,
      end: new Date(
        new Date(holiday.dateUntil).setDate(
          new Date(holiday.dateUntil).getDate() + 1
        )
      ),
      allDay: true,
      color: "#cccccc",
    })),
  ];

  const renderEventContent = (eventInfo) => (
    <>
      <b>{eventInfo.timeText}</b>
      <div
        style={{
          fontSize: "16px",
          padding: "10px",
          fontFamily: "Arial, sans-serif",
          color: "black",
        }}
        dangerouslySetInnerHTML={{ __html: eventInfo.event.title }}
      />
    </>
  );


  return (
    <div className="maincontainer">
      <div id="container" style={{ marginBottom: "20px" }}>
        <FullCalendar
          locales={allLocales}
          locale={"lt"}
          plugins={[
            dayGridPlugin,
            interactionPlugin,
            listPlugin,
            adaptivePlugin,
          ]}
          initialView="dayGridMonth"
          contentHeight="700px"
          headerToolbar={{
            left: "prev,next today",
            center: "title",
            right: "dayGridMonth,dayGridWeek,dayGridDay",
          }}
          events={events}
          weekends={false}
          eventContent={renderEventContent}
          dayHeaderFormat={{
            weekday: "long",
          }}
          dayHeaderClassNames="fc-day-header-black"
          dayHeaderContent={(args) => (
            <div className="fc-day-header">{args.text}</div>
          )}
          dayCellContent={(args) => (
            <div className="fc-day-number">{args.dayNumberText}</div>
          )}
        />
      </div>

      <Grid item sm={10} className="button-container">
        <Stack direction="row" spacing={2}>
          <Link to="/">
            <Button id="back-button-schedule" variant="contained">
              Grįžti
            </Button>
          </Link>
          <Link to={"/planning/" + params.id}>
            <Button id="plan-button-schedule" variant="contained">
              Planavimas
            </Button>
          </Link>
          <Button
            id="print-button-schedule"
            variant="contained"
            onClick={() => window.print()}
          >
            Spausdinti kalendorių
          </Button>

          <div>
            <Button variant="outlined" onClick={handleClickOpen}>
              Tikrinti konfliktus
            </Button>
            <Dialog
              fullScreen={fullScreen}
              open={open}
              onClose={handleClose}
              maxWidth={maxWidth}
              fullWidth={fullWidth}
              aria-labelledby="responsive-dialog-title"
            >
              <DialogTitle id="responsive-dialog-title">
                {"Rasti konfliktai:"}
              </DialogTitle>
              <DialogContent>
                <DialogContentText>
                  {schedule
                    .filter(
                      (item) =>
                        (item.hasTeacherConflict &&
                          item.scheduleIdWithTeacherNameConflict) ||
                        (item.hasClassroomConflict &&
                          item.scheduleIdWithClassroomNameConflict)
                    )
                    .map((item) => (
                      <div key={item.id}>
                        <h3>{`Diena:  ${item.date}`}</h3>
                        {item.hasTeacherConflict &&
                          item.scheduleIdWithTeacherNameConflict && (
                            <div>
                              {Object.entries(
                                item.scheduleIdWithTeacherNameConflict
                              ).map(([key, value]) => (
                                <p key={key}>{`Mokytojas: ${value}`}</p>
                              ))}
                            </div>
                          )}
                        {item.hasClassroomConflict &&
                          item.scheduleIdWithClassroomNameConflict && (
                            <div>
                              {Object.entries(
                                item.scheduleIdWithClassroomNameConflict
                              ).map(([key, value]) => (
                                <p key={key}>{`Klasė: ${value}`}</p>
                              ))}
                            </div>)}
                      </div>))}
                </DialogContentText>
              </DialogContent>
              <DialogActions>
                <Button variant="outlined" onClick={handleClose} autoFocus>
                  Uždaryti
                </Button>
              </DialogActions>
            </Dialog>
          </div>
        </Stack>
      </Grid>
    </div>
  );
}
