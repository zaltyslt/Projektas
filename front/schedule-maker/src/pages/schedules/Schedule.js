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
import adaptivePlugin from '@fullcalendar/adaptive'

export function Schedule() {
  const [weekendsVisible, setWeekendsVisible] = useState(true);
  const [schedule, setSchedule] = useState([]);
  const params = useParams();

  useEffect(() => {
    fetch(`api/v1/schedules/${params.id}/lessons`)
      .then((response) => response.json())
      .then((data) => setSchedule(data))
      .catch((error) => console.error(error));
  }, [params.id]);

  const events = schedule.map(schedule => ({
    title: `<b>${schedule.subject.name}</b>
      <br />
      ${schedule.lessonStart} - ${schedule.lessonEnd}
      <br /> 
      ${schedule.teacher ? schedule.teacher.lName : ""} ${schedule.teacher ? schedule.teacher.fName : "nepasirinktas"}
      <br />
      ${schedule.online ? "Nuotolinė pamoka" : schedule.classroom ? schedule.classroom.classroomName : ""}<br />
      `,
    start: schedule.date,
    allDay: true,
    url: `http://localhost:3000/schedule-maker#/schedules/edit-lesson/${schedule.id}`,
  }));

  const renderEventContent = (eventInfo) => (
    <>
      <b>{eventInfo.timeText}</b>
      <div style={{ fontSize: '16px', padding: '10px', fontFamily: 'Arial, sans-serif', backgroundColor: "#dcedf7", color: "black" }} dangerouslySetInnerHTML={{ __html: eventInfo.event.title }} />
    </>
  );

  return (
    <div className="maincontainer">
      <div id="container" style={{ marginBottom: "20px" }}>
        <FullCalendar
          locales={allLocales}
          locale={"lt"}
          plugins={[dayGridPlugin, interactionPlugin, listPlugin, adaptivePlugin]}
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
            <div className="fc-day-number">
              {args.dayNumberText}
            </div>
          )}
        />
      </div>

      <Grid item sm={10} className="button-container">
        <Stack direction="row" spacing={2}>
          <Link to="/">
            <Button variant="contained">Grįžti</Button>
          </Link>
          <Link to={"/planning/" + params.id}>
            <Button variant="contained">Planavimas</Button>
          </Link>
          <Button variant="contained" onClick={() => window.print()}>Spausdinti kalendorių</Button>
        </Stack>
      </Grid>
    </div>
  );
}