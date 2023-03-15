import React from "react";
import { useParams } from "react-router-dom";
import { Teacher } from "./Teacher";
import { postDataTo, putDataTo, switchActive } from "./components/Fetch";

export function EditTeacher() {
  const params = useParams();
  let result;
   const teacherId = params.id;
     
   async function handleUpdate(teacher) {
  
    if (typeof teacher === "number") {
      result = await switchActive(teacher);
    } 
    else if (typeof teacher === "object") {
      result =  await postDataTo(
        teacher,
        "/api/v1/teachers/update?tid=" + teacher.id,
        "PUT"
      );
    }
   return result;
  }

  return <Teacher mode="update" teacherId={teacherId} onSave={handleUpdate} />;
}
