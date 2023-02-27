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
  
  export function TeacherList() {
    const [teachers, setTeachers] = useState([]);
    const [filteredTeachers, setFilteredTeachers] = useState([]);
    const [deletedTeachers, setDeletedTeachers] = useState([]);
  
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(10);
    const [isChecked, setChecked] = useState(false);
  
    useEffect(() => {
      fetchTeachers();
    }, []);
  
    useEffect(() => {
      fetchDeletedTeachers();
    }, []);
  
    const fetchTeachers = async () => {
      fetch("/api/v1/teachers")
        .then((response) => response.json())
  
        .then((data) => {
          setTeachers(data);
  
          return data;
        })
        .then((data) => setFilteredTeachers(data));
    };
  
    const fetchDeletedTeachers = async () => {
      //fetch("/api/v1/teachers?active=false")
      fetch("/api/v1/teachers")
        .then((response) => response.json())
        .then(setDeletedTeachers);
    };
  
    const emptyRows =
      page > 0 ? Math.max(0, (1 + page) * rowsPerPage - teachers.length) : 0;
  
    const handleChangePage = (event, newPage) => {
      setPage(newPage);
    };
  
    const handleChangeRowsPerPage = (event) => {
      setRowsPerPage(parseInt(event.target.value, 10));
      setPage(0);
    };
  
    const handleSearch = (event) => {
      if (event.length === 0) {
        setFilteredTeachers(teachers);
      } else {
        const filtered = teachers.filter((teacher) => {
          const teacherName = teacher.name.toLowerCase();
          const teacherDescription = teacher.description.toLowerCase();
          return (
            teacherName.includes(event.toLowerCase()) ||
            teacherDescription.includes(event.toLowerCase())
          );
        });
        setFilteredTeachers(filtered);
      }
    };
  
    const handleRestore = (teacher) => {
      fetch("/api/v1/teachers/update?tid=" + teacher.id, {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ id: teacher.id, active: true }),
      })
        .then(fetchTeachers)
        .then(fetchDeletedTeachers);
    };
  
    return (
      <div>
        <Container maxWidth="lg">
          <Grid container rowSpacing={3}>
            <Grid item lg={10}>
              <h3>Mokytojų sąrašas</h3>
            </Grid>
            <Grid item lg={2}>
              <Link to="/teachers/create">
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
                  <TableCell>Vardas Pavardė</TableCell>
                  <TableCell>Žyma</TableCell>
                  <TableCell>Dėstomi dalykai</TableCell>
                  <TableCell>Pamaina</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {(rowsPerPage > 0
                  ? filteredTeachers.slice(
                      page * rowsPerPage,
                      page * rowsPerPage + rowsPerPage
                    )
                  : filteredTeachers
                ).map((teacher) => (
                  <TableRow key={teacher.id}>
                    <TableCell component="th" scope="row">
                      <Link to={"/teachers/view/" + teacher.id}>
                        {teacher.fName +' '+ teacher.lName}
                      </Link>
                    </TableCell>
                    
                    <TableCell>
                      {teacher.nickName && teacher.nickName}
                    </TableCell>
                    <TableCell>
                    {console.log( teacher.subjectsDtoList.length>0 && teacher.subjectsDtoList)}
                      {/* {teacher.subjectsDtoList && teacher.subjectsDtoList} */}
                      TDalykai
                    </TableCell>
                    <TableCell>
                    {teacher.teacherShiftDto ? teacher.teacherShiftDto.name : '* Nepriskirta'}
                    {console.log( teacher.teacherShiftDto)}
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
                    count={filteredTeachers.length}
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
                    <TableCell colSpan={6}>Dalyko pavadinimas keisti2</TableCell>
                    <TableCell colSpan={6}>Modulio pavadinimas keisti2</TableCell>
                    <TableCell colSpan={2} align="center">
                      Veiksmai2
                    </TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {(rowsPerPage > 0
                    ? deletedTeachers.slice(
                        page * rowsPerPage,
                        page * rowsPerPage + rowsPerPage
                      )
                    : deletedTeachers
                  ).map((teacher) => (
                    <TableRow key={teacher.id}>
                      <TableCell colSpan={6} component="th" scope="row">
                        {teacher.fName}
                      </TableCell>
                      <TableCell colSpan={6}>{teacher.active}</TableCell>
                      <TableCell colSpan={2} align="center">
                        <Button onClick={() => handleRestore(teacher)}>
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
                      count={deletedTeachers.length}
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