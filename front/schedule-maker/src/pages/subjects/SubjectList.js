import {
  Button,
  Grid,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableFooter,
  TableHead,
  TablePagination,
  TableRow,
} from "@mui/material";
import { Container } from "@mui/system";

import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

export function SubjectList() {
  const [subjects, setSubjects] = useState([]);

  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);

  useEffect(() => {
    fetch("api/v1/subjects")
      .then((response) => response.json())
      .then(setSubjects);
  }, []);

  const emptyRows =
    page > 0 ? Math.max(0, (1 + page) * rowsPerPage - subjects.length) : 0;

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  return (
    <div>
      <Container maxWidth="lg">
        <Grid container rowSpacing={3}>
          <Grid item lg={10}>
            <h3>Dalykų sąrašas</h3>
          </Grid>
          <Grid item lg={2}>
            <Link to="/subjects/create">
              <Button variant="contained">Pridėti naują</Button>
            </Link>
          </Grid>
        </Grid>

        <TableContainer component={Paper}>
          <Table aria-label="custom pagination table">
            <TableHead>
              <TableRow>
                <TableCell>Dalyko pavadinimas</TableCell>
                <TableCell>Modulio pavadinimas</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {(rowsPerPage > 0
                ? subjects.slice(
                    page * rowsPerPage,
                    page * rowsPerPage + rowsPerPage
                  )
                : subjects
              ).map((subject) => (
                <TableRow key={subject.id}>
                  <TableCell component="th" scope="row">
                    <Link to={"/subjects/view/" + subject.id}>
                     {subject.name}
                    </Link>
                   
                  </TableCell>
                  <TableCell>{subject.description}</TableCell>
                </TableRow>
              ))}

              {emptyRows > 0 && (
                <TableRow style={{ height: 53 * emptyRows }}>
                  <TableCell colSpan={6} />
                </TableRow>
              )}
            </TableBody>
            <TableFooter>
              <TableRow>
                <TablePagination
                  labelRowsPerPage="Rodyti po"
                  rowsPerPageOptions={[10, 20, { label: "Visi", value: -1 }]}
                  colSpan={3}
                  count={subjects.length}
                  page={page}
                  SelectProps={{
                    inputProps: {
                      "aria-label": "Rodyti po",
                    },
                    native: true,
                  }}
                  onPageChange={handleChangePage}
                  rowsPerPage={rowsPerPage}
                  onRowsPerPageChange={handleChangeRowsPerPage}
                ></TablePagination>
              </TableRow>
            </TableFooter>
          </Table>
        </TableContainer>
      </Container>
    </div>
  );
}
