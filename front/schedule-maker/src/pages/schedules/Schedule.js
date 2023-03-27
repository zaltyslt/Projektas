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

export function Schedule() {
  const [weekendsVisible, setWeekendsVisible] = useState(true);
  const [schedule, setSchedule] = useState([]);
  const [holiday, setHoliday] = useState([]);
  const params = useParams();

  const subjectColors = [
    "#f5c5c4",
    "#f5ddc4",
    "#f5f3c4",
    "#daf5c4",
    "#c4f5d8",
    "#c4f5f2",
    "#c4d3f5",
    "#d8c4f5",
    "#d6fc9c",
    "#d5bdf9",
    "#ffcac6",
    "#a3b4ff",
    "#88f7a0",
    "#f1fc8d",
    "#ffc6e5",
    "#81efed",
    "#f49a97",
    "#93c7ff",
    "#f9bd84",
  ];

  const subjectColorMap = {};
  schedule.forEach((s) => {
    if (!subjectColorMap[s.subject.id]) {
      subjectColorMap[s.subject.id] =
        subjectColors[Math.floor(Math.random() * subjectColors.length)];
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

  const events = [
    ...schedule.map((schedule) => {
      const color = subjectColorMap[schedule.subject.id];
      return {
        title: `<b>${schedule.subject.name}</b>
          <br />
          ${schedule.lessonStart} - ${schedule.lessonEnd}
          <br /> 
          ${schedule.teacher ? schedule.teacher.lName : ""} ${
          schedule.teacher ? schedule.teacher.fName : "nepasirinktas"
        }
          <br />
          ${
            schedule.online
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
        </Stack>
      </Grid>
    </div>
  );
}
