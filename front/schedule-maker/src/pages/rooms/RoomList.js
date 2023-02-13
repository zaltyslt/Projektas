import { useEffect, useState } from "react";
import { CreateRoom } from "./CreateRoom";
import { Link, Route, Routes } from "react-router-dom";

export function RoomList() {
  const [classrooms, setClassrooms] = useState([]);
  const [filter, setFilter] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const [classroomsPerPage, setClassroomsPerPage] = useState(10);
  const paginate = (pageNumber) => setCurrentPage(pageNumber);

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

  const filteredClassrooms = classrooms.filter(
    (classroom) => 
      String(classroom.id).toLowerCase().includes(filter.toLowerCase())
  );

  const indexOfLastClassroom = currentPage * classroomsPerPage;
  const indexOfFirstClassroom = indexOfLastClassroom - classroomsPerPage;
  const currentClassrooms = filteredClassrooms.slice(
    indexOfFirstClassroom,
    indexOfLastClassroom
  );

  const pageNumbers = [];
  for (
    let i = 1;
    i <= Math.ceil(filteredClassrooms.length / classroomsPerPage);
    i++
  ) {
    pageNumbers.push(i);
  }

  return (
    <div>
      <h2>Klasių sąrašas</h2>
      <Link to="/create">
        <button>Sukurti naują</button>
      </Link>
      <br></br>
      <br></br>
      <table>
        <thead>
          <tr>
            <th></th>
            <th>Pasirinkti pastatą</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <th>
              <input
                type="text"
                placeholder="Paieška"
                value={filter}
                onChange={(e) => setFilter(e.target.value)}
              ></input>
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
          {filteredClassrooms
            .slice(
              (currentPage - 1) * classroomsPerPage,
              currentPage * classroomsPerPage
            )
            .map((classroom) => (
              <tr key={classroom.id}>
                <td>
                  {/* <Link to={"/classrooms/view" + classroom.id}> */}
                  <Link to={`/classrooms/view/${classroom.id}`}>
                    {classroom.classroomName}
                  </Link>
                </td>
                <td>{classroom.building}</td>
              </tr>
            ))}
        </tbody>
      </table>
      <div>
        <button
          onClick={() => setCurrentPage(currentPage - 1)}
          disabled={currentPage === 1}
        >
          Previous
        </button>
        {currentPage} /{" "}
        {Math.ceil(filteredClassrooms.length / classroomsPerPage)}
        <button
          onClick={() => setCurrentPage(currentPage + 1)}
          disabled={
            currentPage ===
            Math.ceil(filteredClassrooms.length / classroomsPerPage)
          }
        >
          Next
        </button>
      </div>
      <div>
        <input type="checkbox" />
        <label>Ištrintos klasės</label>
      </div>
    </div>
  );
}
