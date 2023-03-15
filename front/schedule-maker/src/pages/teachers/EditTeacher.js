import React from "react";
import { useParams } from "react-router-dom";
import { Teacher } from "./Teacher";
import { postDataTo, putDataTo, switchActive } from "./components/Fetch";

export function EditTeacher() {
  const params = useParams();
  let result;
   const teacherId = params.id;
  console.log(params.id);
   
   async function handleUpdate(teacher) {
  
    if (typeof teacher === "number") {
      console.log("switch active");
      result = await switchActive(teacher);
      console.log(result);
    } 
    else if (typeof teacher === "object") {
      // console.log("update");
      result =  await postDataTo(
        teacher,
        "/api/v1/teachers/update?tid=" + teacher.id,
        "PUT"
        // "/api/v1/teachers/create", "POST"
      );
    }
    console.log(result);
   return result;
  }

  return <Teacher mode="update" teacherId={teacherId} onSave={handleUpdate} />;
}
