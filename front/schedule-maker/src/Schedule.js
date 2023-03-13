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
    currentEvents: []
  };

  render() {
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
            events={[
              {
                title: "Java dalykas yra sudas as noriu miegoti ir valgyti ir miegoti ir valgyti ir miegoti ir valgyti ir miegoti.... Muhahahahhh galiu rasyti tiek kiek noriu!!!",
                date: "2023-03-06",
              },
              { title: "Java", date: "2023-03-07" },
              { title: "Java", date: "2023-03-08" },
              { title: "Java", date: "2023-03-09" },
              { title: "Java", date: "2023-03-10" },
              { title: "Linux", date: "2023-03-14" },
              { title: "Linux", date: "2023-03-15" }
            ]}
            weekends={false}
          />
        </div>
      </div>
    );
  }
}


export default Schedule;