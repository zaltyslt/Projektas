import {
  Button,
  Checkbox,
  FormControlLabel,
  FormGroup,
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
  TextField,
} from "@mui/material";
import { Container } from "@mui/system";

import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

export function SubjectList() {
  const [subjects, setSubjects] = useState([]);
  const [filteredSubjects, setFilteredSubjects] = useState([]);
  const [deletedSubjects, setDeletedSubjects] = useState([]);

  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
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

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const handleSearch = (event) => {
    if (event.length === 0) {
      setFilteredSubjects(subjects);
    } else {
      const filtered = subjects.filter((subject) => {
        const subjectName = subject.name.toLowerCase();
        const subjectDescription = subject.description.toLowerCase();
        return (
          subjectName.includes(event.toLowerCase()) ||
          subjectDescription.includes(event.toLowerCase())
        );
      });
      setFilteredSubjects(filtered);
    }
  };

  const handleRestore = (id) => {
    fetch("/api/v1/subjects/restore/" + id, {
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
          <Grid item lg={10}>
            <h3>Dalykų sąrašas</h3>
          </Grid>
          <Grid item lg={2}>
            <Link to="/subjects/create">
              <Button variant="contained">Pridėti naują</Button>
            </Link>
          </Grid>

          <Grid item lg={12}>
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
                  <TableCell> {subject.module && subject.module.name}</TableCell>
                 
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
                  <TableCell colSpan={6}>Dalyko pavadinimas</TableCell>
                  <TableCell colSpan={6}>Modulio pavadinimas</TableCell>
                  <TableCell colSpan={2} align="center">Veiksmai</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {(rowsPerPage > 0
                  ? deletedSubjects.slice(
                      page * rowsPerPage,
                      page * rowsPerPage + rowsPerPage
                    )
                  : deletedSubjects
                ).map((subject) => (
                  <TableRow key={subject.id}>
                    <TableCell colSpan={6} component="th" scope="row">
                      {subject.name}
                    </TableCell>
                    <TableCell colSpan={6}>{subject.description}</TableCell>
                    <TableCell colSpan={2} align="center">
                      <Button onClick={() => handleRestore(subject.id)}>
                        Atstatyti
                      </Button>
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
                    colSpan={3}
                    count={deletedSubjects.length}
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
        )}
      </Container>
    </div>
  );
}
