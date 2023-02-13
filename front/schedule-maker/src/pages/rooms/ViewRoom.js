import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";

export function ViewRoom() {
  const [classroom, setClassroom] = useState({});
  const params = useParams();
  let navigate = useNavigate();

  useEffect(() => {
    fetch(`/api/v1/classrooms/classroom/${params.id}`)
      .then((response) => response.json())
      .then(setClassroom);
  }, [params.id]);
  return (
    <div>
      <div>
      <h1>{classroom.classroomName}</h1>
      </div>
      <div>
      <h5>Paskutinį kartą redaguota: {classroom.modifiedDate}</h5>
      </div>

      <div>
        <b>Pastatas</b>
      </div>
      <div>{classroom.building}</div>

      <div>
        <b>Klasės aprašas</b>
      </div>
      <div>{classroom.description}</div>
      <br></br>
      <div>
        <Link to={`/update/${classroom.id}`}>
          <button>Redaguoti</button>
        </Link>
        <button onClick={() => navigate(-1)}>Grįžti</button>
      </div>
    </div>
  );
}
