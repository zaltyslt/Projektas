import { useEffect, useState } from "react";
import { CreateRoom } from "./CreateRoom";
import { Link, Route, Routes} from 'react-router-dom';

export function RoomList() {
  const [classrooms, setClassrooms] = useState([]);

  const JSON_HEADERS = {
    "Content-Type": "application/json",
  };

  const fetchClassrooms = () => {
    fetch("/api/v1/classrooms")
      .then((responce) => responce.json())
      .then((jsonResponce) => setClassrooms(jsonResponce));
  };

  useEffect(() => {
    fetchClassrooms();
  }, []);

  return (
    <div>
      <h2>Klasių sąrašas</h2>
      <Link to="/create">
        <button>Sukurti naują</button>
      </Link>
      <table>
      <thead>
        <tr>
          <th></th>
          <th>Pastatą</th>
          <th>Klasę</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <th>
            <input type="text" placeholder="Paieška"></input>
          </th>
          <th>

          </th>
          <th></th>
        </tr>
      </tbody>
      </table>
      <table>
        <thead>
          <tr>
            <th>Klasės pavadinimas</th>
            <th>Pastatas</th>
          </tr>
        </thead>
        <tbody>
          {classrooms.map((classroom) => (
            <tr key={classroom.id}>
              <td>
                <Link to={'/classrooms/view/' + classroom.classroomName}>{classroom.classroomName}</Link>
              </td>
              <td>{classroom.building}</td>
            </tr>
          ))}
        </tbody>
      </table>
      <br></br>
      <h4>Panaikintų klasių sąrašas</h4>
      <table>
        <thead>
          <tr>
            <th>Klasės pavadinimas</th>
            <th>Pastatas</th>
          </tr>
        </thead>
        <tbody>
          {classrooms.map((classroom) => (
            <tr key={classroom.id}>
              <td>
                <Link to={'/classrooms/view/' + classroom.classroomName}>{classroom.classroomName}</Link>
              </td>
              <td>{classroom.building}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
