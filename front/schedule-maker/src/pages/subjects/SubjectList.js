import { MDBTable, MDBTableHead, MDBTableBody, MDBPagination } from "mdb-react-ui-kit";
import { useEffect, useState } from "react";

export function SubjectList() {
  const [subjects, setSubjects] = useState([]);

  useEffect(() => {
    fetch("/api/v1/subjects")
      .then((response) => response.json())
      .then((jsonResponse) => setSubjects(jsonResponse));
  }, []);

  return (
    <div>
      <h3>Dalykų sąrašas</h3>
      <MDBTable hover>
        <MDBTableHead className='table-primary'>
          <tr>
            <th scope="col">#</th>
            <th scope="col">Dalyko pavadinimas</th>
            <th scope="col">Modulio pavadinimas</th>
          </tr>
        </MDBTableHead>
        <MDBTableBody>
            {subjects.map((subject, index) => (
                <tr key={subject.id}>
                    <th scope="row">{index + 1}</th>
                    <td>{subject.name}</td>
                    <td>{subject.module}</td>
                </tr>
            ))}
        </MDBTableBody>
      </MDBTable>
      <MDBPagination />
    </div>
  );
}
