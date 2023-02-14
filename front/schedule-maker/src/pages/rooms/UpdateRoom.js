import { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";

export function UpdateClassroom() {
  const [classroom, setClassroom] = useState({});
  const [error, setError] = useState();
  const [active, setActive] = useState('');
  const [classroomName, setclassroomName] = useState('');
  const [description, setdescription] = useState('');

  const handleDescriptionChange = (event) => {
    setdescription(event.target.value);
  };

  const handleCNameeChange = (event) => {
    setclassroomName(event.target.value);
  };

  const params = useParams({
    classroomName: "",
    building: "",
    description: "",
  });

  // useEffect(() => {
  //   fetch(`/api/v1/classrooms/classroom/${params.id}`)
  //     .then((response) => response.json())
  //     .then(setClassroom);
  // }, []);
  useEffect(() => {
    fetch(`/api/v1/classrooms/classroom/${params.id}`)
      .then((response) => response.json())
      .then(e => {
        params.classroomName = e.classroomName
        setdescription(e.description)
        setActive(e.active)
        setclassroomName(e.classroomName)
      });
  },);

  // const updateClassroom = () => {
  //   fetch("/api/v1/classrooms/update/" + params.id, {
  //     method: "PATCH",
  //     headers: {
  //       "Content-Type": "application/json",
  //     },
  //     body: JSON.stringify(classroom),
  //   }).then((result) => {
  //     if (!result.ok) {
  //       setError("Redaguoti nepavyko!");
  //     } else {
  //       setError();
  //     }
  //   });
  // };

  const updateClassroom = () => {
    fetch(`/api/v1/classrooms/update/${params.id}`, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        description,
        classroomName
      })
    }).then((result) => {
      if (!result.ok) {
        setError('Redaguoti nepavyko!');
      } else {
        setError('Sėkmingai atnaujinote!')
      }
    });
  };

  const disableClassroom = () => {
    fetch(`/api/v1/classrooms/disable/${params.id}`, {
      method: "PATCH",
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        description,
        classroomName
      })
    }).then(() => navigate(-2));
  };

  const enableClassroom = () => {
    fetch(`/api/v1/classrooms/enable/${params.id}`, {
      method: "PATCH",
      headers: {
        'Content-Type': 'application/json',
      }
    }).then(() => navigate(-2));
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
        <div>
          <label>Klasė aktyvi</label>
        </div>
        <input
          disabled={true}
          value={active} />
        <br></br>
        <button onClick={updateClassroom}>Išsaugoti</button>
        {!active &&
          <button
            data-value='true'
            value={params.id}
            onClick={enableClassroom}
          >Aktyvuoti
          </button>
        }
        {/*Ištrinti*/}
        {active &&
          <button
            data-value='true'
            value={params.id}
            onClick={disableClassroom}
          >Ištrinti
          </button>
        }
        {/* <button>Ištrinti</button> */}
        <Link to="/rooms">
          <button>Grįžti</button>
        </Link>
      </fieldset>
    </div>
  );
}
