import React from "react";
import { useParams } from "react-router-dom";
import { Teacher } from "./Teacher";
import { postDataTo, putDataTo, switchActive } from "./components/Fetch";

export function EditTeacher() {
  const { teacherId } = useParams();
  let result;

  async function handleUpdate(teacher) {
    console.log(teacher);

    if (typeof teacher === "number") {
      console.log("switch active");
      result = await switchActive(teacher);
    } else if (typeof teacher === "object") {
      // console.log("update");
      result = await postDataTo(
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
