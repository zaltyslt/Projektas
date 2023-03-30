import React from "react";
import { Teacher } from "./Teacher";
import { postDataTo } from "./components/Fetch";

export function CreateTeacher() {
  async function handleCreate(teacher) {
    const data = await postDataTo(
      teacher,
      "api/v1/teachers/create",
      "POST"
    );
    return data;
  }
  return <Teacher mode="create" onSave={handleCreate} />;
}
