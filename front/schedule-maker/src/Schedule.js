import React from "react";
import "./AppScheduleView.css";
//Fullcalendar and Realted Plugins
// import FullCalendar from '@fullcalendar/react'
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction"; // needed
import listPlugin from "@fullcalendar/list"; //For List View
import allLocales from "@fullcalendar/core/locales-all";

import FullCalendar, { formatDate } from "@fullcalendar/react";
// import timeGridPlugin from '@fullcalendar/timegrid'
// import interactionPlugin from '@fullcalendar/interaction'
// import { INITIAL_EVENTS, createEventId } from './event-utils'

class Schedule extends React.Component {
  state = {
    weekendsVisible: true,
    currentEvents: [],
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
              right: "dayGridMonth,dayGridWeek,dayGridDay,listWeek",
            }}
            events={[
              { title: "Java dalykas yra sudas as noriu miegoti ir valgyti", date: "2023-03-06" },
              { title: "Java", date: "2023-03-07" },
              { title: "Java", date: "2023-03-08" },
              { title: "Java", date: "2023-03-09" },
              { title: "Java", date: "2023-03-10" },
              { title: "Linux", date: "2023-03-14" },
              { title: "Linux", date: "2023-03-15" },
            ]}
            // eventContent={(arg) => {
            //   return (
            //     <div
            //       className="fc-content"
            //       style={{ height: "30px" }}
            //       onClick={() => console.log(arg.event)}
            //     >
            //       <span className="fc-title">{arg.timeText}</span>
            //     </div>
            //   );
            // }}
          />
        </div>
      </div>
    );
  }
}

export default Schedule;