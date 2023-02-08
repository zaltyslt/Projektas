import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";

export function ViewRoom() {
  const [classroom, setClassroom] = useState({});
  const params = useParams();

  useEffect(() => {
    fetch('/api/v1/classrooms' + params.id)
      .then((response) => response.json())
      .then(setClassroom);
  }, [params.id]);
  return (
    <div>
      <div>
        <b>ID</b>
      </div>
      <div>{classroom.id}</div>

      <div>
        <b>Klasės pavadinimas</b>
      </div>
      <div>{classroom.classroomName}</div>

      <div>
        <b>Pastatas</b>
      </div>
      <div>{classroom.building}</div>

      <div>
        <b>Klasės aprašymas</b>
      </div>
      <div>{classroom.description}</div>
    </div>
  );
}
