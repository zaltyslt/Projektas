import React from "react";
import "./AppScheduleView.css";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import listPlugin from "@fullcalendar/list";
import allLocales from "@fullcalendar/core/locales-all";
import FullCalendar from "@fullcalendar/react";

class Schedule extends React.Component {
  state = {
    weekendsVisible: true,
    currentEvents: [],
    scheduleData: []
  };

  componentDidMount() {
    fetch("/api/schedule")
      .then(response => response.json())
      .then(data => this.setState({ scheduleData: data }))
      .catch(error => console.error(error));
  }

  render() {
    const { scheduleData } = this.state;
    const events = scheduleData.map(schedule => ({
      title: schedule.subject.name,
      start: schedule.date,
      end: schedule.date,
      allDay: true
    }));

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
          />
        </div>
      </div>
    );
  }
}

export default Schedule;
