import {
  MDBTable,
  MDBTableHead,
  MDBTableBody,
  MDBPagination,
  MDBPaginationItem,
  MDBPaginationLink,
  MDBBtn,
} from "mdb-react-ui-kit";
import { useEffect, useState } from "react";

export function SubjectList() {
  const [subjects, setSubjects] = useState([]);

  const [currentPage, setCurrentPage] = useState(0);
  const [pageSize, setPageSize] = useState(10);
  const [totalCount, setTotalCount] = useState(0);

  useEffect(() => {
    fetch(
      "/api/v1/subjects/paged?page=" + currentPage + "&pageSize=" + pageSize
    )
      .then((response) => {
        setTotalCount(response.headers.get("totalCount"));

        return response.json();
      })
      .then((jsonResponse) => setSubjects(jsonResponse));
  }, [currentPage, pageSize]);

  const changePage = (value) => {
    setCurrentPage(value);
  };

  const forward = () => {
    setCurrentPage((prevValue) => {
      if (prevValue === totalCount - 1) {
        return prevValue;
      } else {
        return prevValue + 1;
      }
    });
  };

  const back = () => {
    setCurrentPage((prevValue) => {
      if (prevValue === 0) {
        return 0;
      } else {
        return prevValue - 1;
      }
    });
  };

  return (
    <div>
      <MDBBtn>Sukurti naują</MDBBtn>
      <h3>Dalykų sąrašas</h3>
      <MDBTable hover>
        <MDBTableHead className="table-primary">
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
      <MDBPagination>
        <MDBPaginationItem>
          <MDBPaginationLink onClick={() => changePage(0)}>
            Pirmas
          </MDBPaginationLink>
        </MDBPaginationItem>
        <MDBPaginationItem>
          <MDBPaginationLink aria-label="Previous" onClick={back}>
            <span aria-hidden="true">«</span>
          </MDBPaginationLink>
        </MDBPaginationItem>
        <MDBPaginationItem>
          <MDBPaginationLink aria-label="Next" onClick={forward}>
            <span aria-hidden="true">»</span>
          </MDBPaginationLink>
        </MDBPaginationItem>
        <MDBPaginationItem>
          <MDBPaginationLink onClick={() => changePage(totalCount - 1)}>
            Paskutinis
          </MDBPaginationLink>
        </MDBPaginationItem>
      </MDBPagination>
    </div>
  );
}
