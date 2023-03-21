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
import ".././pages.css";

import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

export function TeacherList() {
  const [teachers, setTeachers] = useState([]);
  const [filteredTeachers, setFilteredTeachers] = useState([]);
  
  const [deletedTeachers, setDeletedTeachers] = useState([]);
  const [deletedFiltered, setDeletedFiltered] = useState([]);
  
  const [pageA, setPageA] = useState(0);
  const [pageP, setPageP] = useState(0);

  const [rowsPerPageA, setRowsPerPageA] = useState(10);
  const [rowsPerPageP, setRowsPerPageP] = useState(10);
  const [isChecked, setChecked] = useState(false);

  useEffect(() => {
    fetchTeachers();
  }, []);

  useEffect(() => {
    fetchDeletedTeachers();
  }, []);

  const fetchTeachers = async () => {
    fetch("api/v1/teachers?active=true")
      .then((response) => response.json())
      .then((data) => {
        setTeachers(data);
        return data;
      })
      .then((data) => {
        setFilteredTeachers(data);
      });
  };

  const fetchDeletedTeachers = async () => {
    fetch("api/v1/teachers?active=false")
      .then((response) => response.json())
      .then((data ) => {setDeletedTeachers(data);
      return data})
      .then((data) => {
        setDeletedFiltered(data);
      });
  };

  const emptyRowsA =
    pageA > 0 ? Math.max(0, (1 + pageA) * rowsPerPageA - teachers.length) : 0;
  const emptyRowsP =
    pageP > 0 ? Math.max(0, (1 + pageP) * rowsPerPageA - teachers.length) : 0;

  const handleChangePageA = (event, newPage) => {
    setPageA(newPage);
  };

  const handleChangePageP = (event, newPageP) => {
    setPageP(newPageP);
  };

  const handleChangeRowsPerPageA = (event) => {
    setRowsPerPageA(parseInt(event.target.value, 10));
    setPageA(0);
  };

  const handleChangeRowsPerPageP = (event) => {
    setRowsPerPageP(parseInt(event.target.value, 10));
    setPageP(0);
  };
  //////////////////////
  const handleSearch = (event) => {
      if (event.length === 0) {
      setFilteredTeachers(teachers);
    } else {
      const filtered = teachers.filter((teacher) => {
        const teacherFName = teacher.fName.toLowerCase();
        const teacherLName = teacher.lName.toLowerCase();
        const shift = teacher.selectedShift.name.toLowerCase();
        const moduleNamesArray = teacher.subjectsList
          .map((subject) => subject.name.toLowerCase())
          .flat();

        return (
          teacherFName.includes(event.toLowerCase()) ||
          teacherLName.includes(event.toLowerCase()) ||
          shift.includes(event.toLowerCase()) ||
          moduleNamesArray.some((name) => name.includes(event.toLowerCase()))
          );
        });
        
        setFilteredTeachers(filtered);
        setPageA(0);
    }
    if (isChecked) {
      if (event.length === 0) {
        setDeletedFiltered(deletedTeachers);
      } else {
        const deletedFiltered = deletedTeachers.filter((teacher) => {
          const teacherFName = teacher.fName.toLowerCase();
          const teacherLName = teacher.lName.toLowerCase();
          // const shift = teacher.selectedShift.name.toLowerCase();
          const subjectsNamesArray = teacher.subjectsList
            .map((subject) => subject.name.toLowerCase())
            .flat();

          return (
            teacherFName.includes(event.toLowerCase()) ||
            teacherLName.includes(event.toLowerCase()) ||
            // shift.includes(event.toLowerCase()) ||
            subjectsNamesArray.some((name) => name.includes(event.toLowerCase()))
          );
        });
       
        setDeletedFiltered(deletedFiltered);
        setPageP(0);
      }
    }
  };

  //////////
  const restoreTeacher = async (teacher) => {
    await fetch(`api/v1/teachers/active?tid=${teacher.id}&active=true`, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then(fetchTeachers)
      .then(fetchDeletedTeachers);
  };

  return (
    <div>
      <Container maxWidth="lg">
        <Grid container rowSpacing={3}>
          <Grid item sm={10}>
            <h3>Mokytojų sąrašas</h3>
          </Grid>
          <Grid item sm={2}>
            <Stack direction="row" justifyContent="flex-end">
              <Link to="/teachers/create">
                <Button id="create-new-teacher" variant="contained">Pridėti naują</Button>
              </Link>
            </Stack>
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
                <TableCell style={{ width: "44%" }}>Vardas Pavardė</TableCell>
                <TableCell style={{ width: "45%" }}>
                  Dėstomi dalykai
                </TableCell>
                <TableCell >
                  Pamaina
                </TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {(rowsPerPageA > 0
                ? filteredTeachers.slice(
                    pageA * rowsPerPageA,
                    pageA * rowsPerPageA + rowsPerPageA
                  )
                : filteredTeachers
              ).map((teacher) => (
                <TableRow key={teacher.id}>
                  <TableCell component="th" scope="row" align="left">
                    <Link to={"/teachers/view/" + teacher.id}>
                      {teacher.fName + " " + teacher.lName}
                    </Link>
                  </TableCell>

                  <TableCell>
                    {teacher.subjectsList && teacher.subjectsList.length > 0
                      ? teacher.subjectsList.map((subjectItem, index) => {
                          return <p key={index}>{subjectItem.name}</p>;
                        })
                      : ""}
                  </TableCell>

                  <TableCell>
                    {teacher.selectedShift ? teacher.selectedShift.name : ""}
                  </TableCell>
                </TableRow>
              ))}

              {/* //////////////////////////// */}
              {emptyRowsA > 0 && (
                <TableRow style={{ height: 53 * emptyRowsA }}>
                  <TableCell colSpan={6} />
                </TableRow>
              )}
            </TableBody>
            <TableFooter>
              <TableRow>
                <TablePagination
                  labelRowsPerPage="Rodyti po"
                  labelDisplayedRows={({ from, to, count }) =>
                    `${from}-${to} iš ${count}`
                  }
                  rowsPerPageOptions={[10, 20, { label: "Visi", value: -1 }]}
                  colSpan={3}
                  count={filteredTeachers.length}
                  page={pageA}
                  SelectProps={{
                    inputProps: { "aria-label": "Rodyti po" },
                    native: true,
                  }}
                  //  Active
                  onPageChange={handleChangePageA}
                  rowsPerPage={rowsPerPageA}
                  onRowsPerPageChange={handleChangeRowsPerPageA}
                ></TablePagination>
              </TableRow>
            </TableFooter>
          </Table>
        </TableContainer>

        <FormGroup>
          <FormControlLabel
            control={<Checkbox />}
            label="Ištrinti mokytojai"
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
                  <TableCell style={{ width: "45%" }}>
                    Vardas Pavardė
                  </TableCell>
                  <TableCell style={{ width: "45%" }}>
                    Dėstomi dalykai
                  </TableCell>
                  <TableCell align="center">
                    Veiksmai
                  </TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {(rowsPerPageP > 0
                  ? deletedFiltered.slice(
                      pageP * rowsPerPageP,
                      pageP * rowsPerPageP + rowsPerPageP
                    )
                  : deletedFiltered
                ).map((teacher) => (
                  <TableRow key={teacher.id}>
                    {/* <TableCell colSpan={6} component="th" scope="row">
                      {teacher.fName}
                    </TableCell>
                    <TableCell colSpan={6}>{teacher.active}</TableCell>
                    <TableCell colSpan={2} align="center"> */}
                    <TableCell component="th" scope="row">
                      {`${teacher.fName} ${teacher.lName}`}
                    </TableCell>
                    <TableCell>
                      {teacher.subjectsList && teacher.subjectsList.length > 0
                        ? teacher.subjectsList.map((subjectItem, index) => {
                            {
                              /* const subject = subjects.find(
                            (s) => s.id === subjectItem.subjectId
                          ); */
                            }
                            return <p key={index}>{subjectItem.name}</p>;
                          })
                        : "* Nepriskirta"}
                    </TableCell>
                    <TableCell align="center">
                      <Button
                        variant="contained"
                        onClick={() => restoreTeacher(teacher)}
                      >
                        Atstatyti
                      </Button>
                    </TableCell>
                  </TableRow>
                ))}

                {emptyRowsP > 0 && (
                  <TableRow style={{ height: 53 * emptyRowsP }}>
                    <TableCell colSpan={3} />
                  </TableRow>
                )}
              </TableBody>
              <TableFooter>
                <TableRow>
                  <TablePagination
                    id="inactive"
                    labelRowsPerPage="Rodyti po"
                    rowsPerPageOptions={[10, 20, { label: "Visi", value: -1 }]}
                    colSpan={3}
                    count={deletedTeachers.length}
                    page={pageP}
                    labelDisplayedRows={({ from, to, count }) =>
                      `${from}-${to} iš ${count}`
                    }
                    SelectProps={{
                      inputProps: {
                        "aria-label": "Rodyti po",
                      },
                      native: true,
                    }}
                    //inactive
                    onPageChange={handleChangePageP}
                    rowsPerPage={rowsPerPageP}
                    onRowsPerPageChange={handleChangeRowsPerPageP}
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
