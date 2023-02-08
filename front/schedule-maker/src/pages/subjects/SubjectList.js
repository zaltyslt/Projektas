import {
  MDBTable,
  MDBTableHead,
  MDBTableBody,
  MDBPagination,
  MDBPaginationItem,
  MDBPaginationLink,
} from "mdb-react-ui-kit";
import { useEffect, useState } from "react";

export function SubjectList() {
  const [subjects, setSubjects] = useState([]);

  const [currentPage, setCurrentPage] = useState(0);
  const [pageSize, setPageSize] = useState(10);
  const [totalCount, setTotalCount] = useState(0);

  useEffect(() => {
    fetch("/api/v1/subjects/paged?page=" + currentPage + "&pageSize=" + pageSize)
      .then((response) => {
        setTotalCount(response.headers.get("totalCount"));
        
        return response.json()})
      .then((jsonResponse) => setSubjects(jsonResponse));
  }, [currentPage, pageSize]);

  const changePage = (value) => {
    setCurrentPage(value);
  }

  return (
    <div>
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
          <MDBPaginationLink href="#">Previous</MDBPaginationLink>
        </MDBPaginationItem>
        <MDBPaginationItem>
          <MDBPaginationLink href="#">1</MDBPaginationLink>
        </MDBPaginationItem>
        <MDBPaginationItem>
          <MDBPaginationLink onClick={(e) => changePage(2)}>...</MDBPaginationLink>
        </MDBPaginationItem>
        <MDBPaginationItem>
          <MDBPaginationLink>{totalCount}</MDBPaginationLink>
        </MDBPaginationItem>
        <MDBPaginationItem>
          <MDBPaginationLink href="#">Next</MDBPaginationLink>
        </MDBPaginationItem>
      </MDBPagination>
    </div>
  );
}
