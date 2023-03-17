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

  console.log(schedule);

  const events = schedule.map(schedule => ({
    title: `<b>${schedule.subject.name}</b>
      <br />
      ${schedule.lessonStart} - ${schedule.lessonEnd}
      <br /> 
      ${schedule.teacher ? schedule.teacher.lName : ""} ${schedule.teacher ? schedule.teacher.fName : "nepasirinktas"}
      <br />
      ${schedule.classroom ? schedule.classroom.classroomName : "nepasirinkta"}
      <br />
      Nuotolinė pamoka:
      ${schedule.online === true ? "taip" : "ne"}
      <br />
      `,
    start: schedule.date,
    allDay: true,
  }));

  const renderEventContent = (eventInfo) => (
    <>
      <b>{eventInfo.timeText}</b>
      <div style={{fontSize: '16px', padding: '10px', fontFamily: 'Arial, sans-serif', backgroundColor: "#dcedf7", color: "black" }} dangerouslySetInnerHTML={{ __html: eventInfo.event.title }} />
    </>
  );

  // const renderEventContent = (eventInfo) => (
  //   <>
  //     <b>{eventInfo.timeText}</b>
  //     <div dangerouslySetInnerHTML={{ __html: eventInfo.event.title }} />
  //   </>
  // );

  return (
    <div className="maincontainer">
      <div id="container">
        <FullCalendar
          locales={allLocales}
          locale={"lt"}
          plugins={[dayGridPlugin, interactionPlugin, listPlugin]}
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
            weekday: "long", // or 'short'
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

      <Grid item sm={10}>
        <Stack direction="row" spacing={2}>
          <Link to="/">
            <Button variant="contained">Grįžti</Button>
          </Link>
          <Link to={"/planning/" + params.id}>
            <Button variant="contained">Planavimas</Button>
          </Link>
        </Stack>
      </Grid>
    </div>
  );
}
