import React from "react";
import { useParams } from "react-router-dom";
import {Teacher } from "./Teacher";
import { postDataTo } from "./components/Fetch";

export function UpdateEntityPage() {
  const { teacherId } = useParams();
 

  function handleUpdate(teacher) {
    // Make an API request to update the entity
    console.log("Updating teacher", teacher);
  //   id: 0,
  //   fName: fName,
  //   lName: lName,
  //   active: true,
  //   workHoursPerWeek: workHours,
  // };
  // const teacherContacts = {
  //   phoneNumber,
  //   directEmail,
  //   teamsEmail,
  //   teamsName,
  // };

  // const teacherShiftDto = {
  //   id: selectedShift.id,
  //   name: selectedShift.name,
  // };
    postDataTo(
      {
      id: teacher.id,
        fName: teacher.fName,
        lName: teacher.lName,
        active: true,
        workHoursPerWeek: teacher.workHoursPerWeek,
        contacts : teacher.contacts,
        teacherShiftDto: teacher.teacherShiftDto,
        subjectsList: teacher.subjects,
      

        // body: body,
        // contacts: teacherContacts,
        // shift: teacherShiftDto,
        // subjects: chosenSubjects,
      },
      (data) => {
        applyResult(data);
      }
    );
  }

  return <Teacher mode="update" teacherId={teacherId} onSave={handleUpdate} />;
}


