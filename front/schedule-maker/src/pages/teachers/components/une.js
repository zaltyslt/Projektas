import React, { useState, useEffect } from "react";
import { getDataFrom, postDataTo } from "./Fetch";

export function EntityForm({ mode, entityId, onSave }) {
  const [entity, setEntity] = useState({});
  const [subjects, setSubjects] = useState([]);
  const [subject, setSubject] = useState("");
  const [chosenSubjects, setChosenSubjects] = useState([]);
  const [freeSubjects, setFreeSubjects] = useState([]);
  const [shifts, setShifts] = useState([]);
  const [selectedShift, setSelectedShift] = useState("");

  // Fetch the entity data if in update mode
  useEffect(() => {
    fetchData();
    if (mode === "update") {
      fetchTeacherData();

      //   console.log("une fetch data");
    }
  }, []);

  async function fetchData() {
    getDataFrom("api/v1/teachers/subjects", (data) => {
      setSubjects(data.body);
      setFreeSubjects(data.body);
    });

    getDataFrom("api/v1/shift/get-active", (data) => {
      setShifts(data.body);
    });
  }

  async function fetchTeacherData() {
    getDataFrom("api/v1/teachers/view?tid=" + entityId, (data) => {
      setEntity(data.body);
      console.log(data);
    });
  }

  function handleChange(event) {
    const { name, value } = event.target;
    setEntity((prevEntity) => ({ ...prevEntity, [name]: value }));
  }

  function handleSubmit(event) {
    event.preventDefault();
    onSave(entity);
  }

  return (
    <form onSubmit={handleSubmit}>
      <h4>Une</h4>
      <p>{mode}</p>

      <input
        type="text"
        name="name"
        value={entity.fName || ""}
        onChange={handleChange}
      />
      <input
        type="text"
        name="description"
        value={entity.lName || ""}
        onChange={handleChange}
      />
      <button type="submit">{mode === "update" ? "Save" : "Create"}</button>
    </form>
  );
}

