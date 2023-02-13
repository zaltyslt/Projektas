import { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";

export function UpdateClassroom() {
  const params = useParams({
    classroomName: "",
    building: "",
    description: "",
  });

  const [classroom, setClassroom] = useState({});
  const [error, setError] = useState();

  useEffect(() => {
    fetch("/api/v1/classrooms/update/" + params.id)
      .then((response) => response.json())
      .then(setClassroom);
  }, []);

  const updateClassroom = () => {
    fetch("/api/v1/classrooms/update/" + params.id, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(classroom),
    }).then((result) => {
      if (!result.ok) {
        setError("Redaguoti nepavyko!");
      } else {
        setError();
      }
    });
  };

  const updateProperty = (property, event) => {
    setClassroom({
      ...classroom,
      [property]: event.target.value,
    });
  };

  return (
    <div>
      <h2>Redagavimas</h2>
      <fieldset>
        <legend>{params.classroomName}</legend>
        {error && <div classroomName="error">{error}</div>}
        <div>
        <label>Klasės pavadinimas</label>
        </div>
        <div>
        <input
          value={classroom.classroomName}
          onChange={(e) => updateProperty("classroomName", e)}
        />
        </div>
        <div>
        <label>Klasės aprašymas</label>
        </div>
        <div>
        <input
          value={classroom.description}
          onChange={(e) => updateProperty("description", e)}
        />
        </div>
        <br></br>
        <button onClick={updateClassroom}>Išsaugoti</button>
        <button>Ištrinti</button>
        <Link to="/rooms">
          <button>Grįžti</button>
        </Link>
      </fieldset>
    </div>
  );
}
