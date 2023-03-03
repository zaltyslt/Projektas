import {
  Button,
  Checkbox,
  FormControlLabel,
  FormGroup,
  Grid,
  Paper,
  Stack,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableFooter,
  TableHead,
  TablePagination,
  TableRow,
  TextField,
} from "@mui/material";
import { Container } from "@mui/system";
import "./Subject.css";

import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

export function SubjectList() {
  const [subjects, setSubjects] = useState([]);
  const [filteredSubjects, setFilteredSubjects] = useState([]);
  const [deletedSubjects, setDeletedSubjects] = useState([]);

  const [page, setPage] = useState(0);
  const [pageInDeleted, setPageInDeleted] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [rowsPerPageInDeleted, setRowsPerPageInDeleted] = useState(10);
  const [isChecked, setChecked] = useState(false);

  useEffect(() => {
    fetchSubjects();
  }, []);

  useEffect(() => {
    fetchDeletedSubjects();
  }, []);

  const fetchSubjects = () => {
    fetch("api/v1/subjects")
      .then((response) => response.json())
      .then((data) => {
        setSubjects(data);
        return data;
      })
      .then((data) => setFilteredSubjects(data));
  };

  const fetchDeletedSubjects = () => {
    fetch("api/v1/subjects/deleted")
      .then((response) => response.json())
      .then(setDeletedSubjects);
  };

  const emptyRows =
    page > 0 ? Math.max(0, (1 + page) * rowsPerPage - subjects.length) : 0;
  
  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const emptyRowsInDeleted =
    pageInDeleted > 0 ? Math.max(0, (1 + pageInDeleted) * rowsPerPageInDeleted - deletedSubjects.length) : 0;

  const handleChangePageInDeleted = (event, newPage) => {
    setPageInDeleted(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const handleChangeRowsPerPageInDeleted = (event) => {
    setRowsPerPageInDeleted(parseInt(event.target.value, 10));
    setPageInDeleted(0);
  };

  const handleSearch = (event) => {
    if (event.length === 0) {
      setFilteredSubjects(subjects);
    } else {
      const filtered = subjects.filter((subject) => {
        const subjectName = subject.name.toLowerCase();

        if (!subject.module) {
          return subjectName.includes(event.toLowerCase());
        } else {
          const subjectModuleName = subject.module.name.toLowerCase();
          return (
            subjectName.includes(event.toLowerCase()) ||
            subjectModuleName.includes(event.toLowerCase())
          );
        }
      });
      setFilteredSubjects(filtered);
    }
  };

  const handleRestore = (id) => {
    fetch("api/v1/subjects/restore/" + id, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then(fetchSubjects)
      .then(fetchDeletedSubjects);
  };

  return (
    <div>
      <Container maxWidth="lg">
        <Grid container rowSpacing={3}>
          <Grid item sm={10}>
            <h3>Dalykų sąrašas</h3>
          </Grid>
          <Grid item sm={2}>
            <Link to="/subjects/create">
              <Stack direction="row" justifyContent="flex-end">
                <Button variant="contained">Pridėti naują</Button>
              </Stack>
            </Link>
          </Grid>

          <Grid item sm={12}>
            <TextField
              fullWidth
              variant="outlined"
              name="search-form"
              label="Paieška"
              id="search-form"
              onChange={(e) => handleSearch(e.target.value)}
            ></TextField>
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
                ? filteredSubjects.slice(
                    page * rowsPerPage,
                    page * rowsPerPage + rowsPerPage
                  )
                : filteredSubjects
              ).map((subject) => (
                <TableRow key={subject.id}>
                  <TableCell component="th" scope="row">
                    <Link to={"/subjects/view/" + subject.id}>
                      {subject.name}
                    </Link>
                  </TableCell>
                  <TableCell>
                    {subject.module ? (
                      subject.module.deleted ? (
                        <span className="Deleted">
                          {subject.module.name}
                        </span>
                      ) : (
                        subject.module.name
                      )
                    ) : (
                      <span>Nenurodytas</span>
                    )}
                  </TableCell>
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
                  labelDisplayedRows={({ from, to, count }) =>
                    `${from}-${to} iš ${count}`
                  }
                  colSpan={3}
                  count={filteredSubjects.length}
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

        <FormGroup>
          <FormControlLabel
            control={<Checkbox />}
            label="Ištrinti dalykai"
            onChange={(e) =>
              e.target.checked ? setChecked(true) : setChecked(false)
            }
          />
        </FormGroup>
        {isChecked && (
          <TableContainer component={Paper}>
            <Table aria-label="custom pagination table">
              <TableHead>
                <TableRow>
                  <TableCell>Dalyko pavadinimas</TableCell>
                  <TableCell>Modulio pavadinimas</TableCell>
                  <TableCell className="activity"></TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {(rowsPerPageInDeleted > 0
                  ? deletedSubjects.slice(
                      pageInDeleted * rowsPerPageInDeleted,
                      pageInDeleted * rowsPerPageInDeleted + rowsPerPageInDeleted
                    )
                  : deletedSubjects
                ).map((subject) => (
                  <TableRow key={subject.id}>
                    <TableCell component="th" scope="row">
                      {subject.name}
                    </TableCell>
                    <TableCell>
                      {subject.module ? (
                        subject.module.deleted ? (
                          <span className="Deleted">
                            {subject.module.name}
                          </span>
                        ) : (
                          subject.module.name
                        )
                      ) : (
                        <span>Nenurodytas</span>
                      )}
                    </TableCell>
                    <TableCell align="center" className="activity">
                      <Button
                        variant="contained"
                        onClick={() => handleRestore(subject.id)}
                      >
                        Atstatyti
                      </Button>
                    </TableCell>
                  </TableRow>
                ))}

                {emptyRowsInDeleted > 0 && (
                  <TableRow style={{ height: 53 * emptyRowsInDeleted }}>
                    <TableCell colSpan={6} />
                  </TableRow>
                )}
              </TableBody>
              <TableFooter>
                <TableRow>
                  <TablePagination
                    labelRowsPerPage="Rodyti po"
                    rowsPerPageOptions={[10, 20, { label: "Visi", value: -1 }]}
                    labelDisplayedRows={({ from, to, count }) =>
                      `${from}-${to} iš ${count}`
                    }
                    colSpan={3}
                    count={deletedSubjects.length}
                    page={pageInDeleted}
                    SelectProps={{
                      inputProps: {
                        "aria-label": "Rodyti po",
                      },
                      native: true,
                    }}
                    onPageChange={handleChangePageInDeleted}
                    rowsPerPage={rowsPerPageInDeleted}
                    onRowsPerPageChange={handleChangeRowsPerPageInDeleted}
                  ></TablePagination>
                </TableRow>
              </TableFooter>
            </Table>
          </TableContainer>
        )}
      </Container>
    </div>
  );
}
