import React, { useState, useEffect } from "react";
import "./AppScheduleView.css";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import listPlugin from "@fullcalendar/list";
import allLocales from "@fullcalendar/core/locales-all";
import FullCalendar from "@fullcalendar/react";

export function Schedule() {
  const [weekendsVisible, setWeekendsVisible] = useState(true);
  const [schedule, setSchedule] = useState([]);

  useEffect(() => {
      fetch("http://localhost:8080/schedule-maker/api/v1/schedules/2/lessons")
        .then(response => response.json())
        .then(data => setSchedule(data))
        .catch(error => console.error(error));
  }, [1]
  );

  console.log(schedule)

  // const events = schedule.map(schedule => ({
  //   title: `${schedule.subject.name}
  //   <br />
  //   ${schedule.teacher.lName}
  //   <br />
  //   ${schedule.subject.classRooms[0].classroomName}`,
  //   start: schedule.schedule.dateFrom,
  //   end: schedule.schedule.dateUntil,
  //   allDay: true,
  // }));

  const events = schedule.map(schedule => ({
    title: `${schedule.subject.name}
      <br />
      ${schedule.teacher.lName}
      <br />
      ${schedule.subject.classRooms.map(classroom => `${classroom.classroomName} (${classroom.classroomCapacity})`).join("<br/>")}`,
    start: schedule.schedule.dateFrom,
    end: schedule.schedule.dateUntil,
    allDay: true,
  }));
  

  const renderEventContent = (eventInfo) => (
    <>
      <b>{eventInfo.timeText}</b>
      <div dangerouslySetInnerHTML={{ __html: eventInfo.event.title }} />
    </>
  );

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
            right: "dayGridMonth,dayGridWeek,dayGridDay,listWeek"
          }}
          events={events}
          weekends={false}
          eventContent={renderEventContent}
        />
      </div>
    </div>
  );
}
