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
import { eachDayOfInterval } from "date-fns";

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
  const [schedules, setSchedules] = useState([]);
  const [holiday, setHoliday] = useState([]);
  const params = useParams();
  const [open, setOpen] = React.useState(false);
  const theme = useTheme();
  const fullScreen = useMediaQuery(theme.breakpoints.down('md'));
  const [maxWidth, setMaxWidth] = React.useState('md');
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

  const subjectColorMap = { index: 0 };
  schedule.forEach((s) => {
    if (!subjectColorMap[s.subject.id]) {
      subjectColorMap[s.subject.id] = subjectColors[subjectColorMap.index];
      subjectColorMap.index += 1;
      subjectColorMap.index + 1 === subjectColors.length
        ? (subjectColorMap.index = 0)
        : (subjectColorMap.index += 1);
    }
  });

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

  useEffect(() => {
    const div = document.querySelector(".fc-license-message");
    div.style.visibility = "hidden";
  }, []);

  useEffect(() => {
    checkConflictsOnClick();
  }, [schedule]);

  const checkConflictsOnClick = async () => {
    const promises = [];
    const seenKeys = new Set();

    schedule.forEach((item) => {
      if (item.hasTeacherConflict && item.scheduleIdWithTeacherNameConflict) {
        Object.entries(item.scheduleIdWithTeacherNameConflict).forEach(([key, value]) => {
          if (!seenKeys.has(key)) {
            promises.push(fetch(`api/v1/schedules/schedule/${key}`).then((response) => response.json()));
            seenKeys.add(key);
          }
        });
      }
      if (item.hasClassroomConflict && item.scheduleIdWithClassroomNameConflict) {
        Object.entries(item.scheduleIdWithClassroomNameConflict).forEach(([key, value]) => {
          if (!seenKeys.has(key)) {
            promises.push(fetch(`api/v1/schedules/schedule/${key}`).then((response) => response.json()));
            seenKeys.add(key);
          }
        });
      }
    });
    const results = await Promise.all(promises);
    setSchedules(results);
  };

  const handleClickPrint = (scheduleId, paged) => {
    const fetchTo = paged
      ? `api/v1/schedules/excel?id=${scheduleId}&p=true`
      : `api/v1/schedules/excel?id=${scheduleId}&p=false`;
    fetch(fetchTo, {
      method: "Get",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => {
        if (response.ok) {
        } else {
        }
        return response;
      })
      .then((response) => {
        const filename = response.headers
          .get("Content-Disposition")
          .split("filename=")[1];
        response.blob().then((blob) => {
          let url = window.URL.createObjectURL(blob);
          let a = document.createElement("a");
          a.href = url;
          a.download = filename;
          a.click();
        });
      });
  };

  const events = [
    ...schedule.map((schedule) => {
      const color = subjectColorMap[schedule.subject.id];
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
            : schedule.classroom ? schedule.classroom.classroomName : ""
          }<br />
          `,
        start: schedule.date,
        allDay: true,
        url: `api/v1/schedules/edit-lesson/${schedule.id}`,
        color: color,
      };
    }),
    ...holiday.flatMap((holiday) => {
      const holidayDates = eachDayOfInterval({
        start: new Date(holiday.dateFrom),
        end: new Date(holiday.dateUntil),
      });
      return holidayDates.map((date) => ({
        title: `<div style="margin-top: 37px; margin-bottom: 37px;"><b>${holiday.name}</b></div>`,
        start: date,
        allDay: true,
        url: `hapi/v1/schedules/edit-holidays/${holiday.id}`,
        color: "#cccccc",
      }));
    }),
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
          <Link to={"/planning/" + params.id}>
            <Button id="plan-button-schedule" variant="contained">
              Planavimas
            </Button>
          </Link>
          <Button
            variant="contained"
            onClick={() => handleClickPrint(params.id, true)}
          >
            SPAUSDINTI EXCEL
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
                  {schedule && schedule.length > 0 ? (schedule
                    .filter(
                      (item) =>
                        (item.hasTeacherConflict &&
                          item.scheduleIdWithTeacherNameConflict) ||
                        (item.hasClassroomConflict &&
                          item.scheduleIdWithClassroomNameConflict)
                    )
                    .map((item) => (
                      <div key={item.id}>
                        <h3>Diena: {item.date}</h3>
                        {item.hasTeacherConflict &&
                          item.scheduleIdWithTeacherNameConflict && (
                            <div>
                              {Object.entries(
                                item.scheduleIdWithTeacherNameConflict
                              ).map(([key, value]) => (
                                <div key={key}>
                                  <p>Mokytojas: {value}</p>
                                  {schedules
                                    .filter(
                                      (scheduleItem) =>
                                        item.scheduleIdWithTeacherNameConflict[scheduleItem.id]
                                    )
                                    .map((scheduleItem) => (
                                      <p key={scheduleItem.id}>{`Mokslo metai: ${scheduleItem.schoolYear}, pavadinimas: ${scheduleItem.semester}, laikotarpis ${scheduleItem.dateFrom} — ${scheduleItem.dateUntil}`}</p>
                                    ))}
                                </div>
                              ))}
                            </div>
                          )}
                        {item.hasClassroomConflict &&
                          item.scheduleIdWithClassroomNameConflict && (
                            <div>
                              {Object.entries(
                                item.scheduleIdWithClassroomNameConflict
                              ).map(([key, value]) => (
                                <div key={key}>
                                  <p>Klasė: {value}</p>
                                  {schedules
                                    .filter(
                                      (scheduleItem) =>
                                        item.scheduleIdWithClassroomNameConflict[scheduleItem.id]
                                    )
                                    .map((scheduleItem) => (
                                      <p key={scheduleItem.id}>{`Mokslo metai: ${scheduleItem.schoolYear}, pavadinimas: ${scheduleItem.semester}, laikotarpis ${scheduleItem.dateFrom} — ${scheduleItem.dateUntil}`}</p>
                                    ))}
                                </div>
                              ))}
                            </div>
                          )}
                      </div>
                    ))) : (
                    <p>Konfliktų nėra.</p>
                  )}
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
