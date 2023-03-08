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
  // const [subjects, setSubjects] = useState([]);

  const [page, setPage] = useState(0);
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

  // useEffect(() => {
  //   fetchSubjects();
  // }, []);

  const fetchTeachers = async () => {
    fetch("/api/v1/teachers?active=true")
      .then((response) => response.json())
      .then((data) => {
        setTeachers(data);
        // console.log(data);
        return data;
      })
      .then((data) => {
        setFilteredTeachers(data);
      });
  };

  const fetchDeletedTeachers = async () => {
    fetch("/api/v1/teachers?active=false")
      .then((response) => response.json())
      .then(setDeletedTeachers);
  };

  

  const emptyRows =
    page > 0 ? Math.max(0, (1 + page) * rowsPerPageA - teachers.length) : 0;

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPageA(parseInt(event.target.value, 10));
    setPage(0);
  };

  const handleChangeRowsPerPageP = (event) => {
    setRowsPerPageP(parseInt(event.target.value, 10));
    setPageP(0);
  };

  // const handleSearch = (event) => {
  //   if (event.length === 0) {
  //     setFilteredModules(modules);
  //   } else {
  //     const filtered = modules.filter((module) => {
  //       const moduleName = module.name.toLowerCase();
  //       return moduleName.includes(event.toLowerCase());
  //     });
  //     setFilteredModules(filtered);
  //   }
  // };



  const handleSearch = (event) => {
    // console.log(teachers);
    if (event.length === 0) {setFilteredTeachers(teachers); } 
    else {
      const filtered = teachers.filter((teacher) => {
        const teacherFName = teacher.fName.toLowerCase();
        const teacherLName = teacher.lName.toLowerCase();
        const shift = teacher.teacherShiftDto.name.toLowerCase();
        const moduleNamesArray = teacher.subjectsList.map(subject => subject.name.toLowerCase()).flat();
      console.log(moduleNamesArray);
        
        // const subjectsF = teacher.subjectsList.map();
      
        return (teacherFName.includes(event.toLowerCase()) 
        ||teacherLName.includes(event.toLowerCase()) 
        || shift.includes(event.toLowerCase())
        || moduleNamesArray.some(name => name.includes(event.toLowerCase()))
        );
      });
      
      // console.log(filtered);
      setFilteredTeachers(filtered);
    }
  };
  
  //////////
  const restoreTeacher = async (teacher) => {
    await fetch(`/api/v1/teachers/active?tid=${teacher.id}&active=true`, {
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
          {/* <Stack direction="row" justifyContent="flex-end">
            <Link to="/teachers/create">
              <Button variant="contained">Pridėti naują</Button>
            </Link>
            </Stack> */}
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
                <TableCell >Vardas Pavardė</TableCell>
                <TableCell >Dėstomi dalykai</TableCell>
                <TableCell className="empty-activity">Pamaina</TableCell>
                {/* <TableCell></TableCell> */}
              </TableRow>
            </TableHead>
            <TableBody>
              {(rowsPerPageA > 0
                ? filteredTeachers.slice(
                    page * rowsPerPageA,
                    page * rowsPerPageA + rowsPerPageA
                  )
                : filteredTeachers
              ).map((teacher) => (
                <TableRow key={teacher.id}>
                  <TableCell component="th" scope="row">
                    <Link to={"/teachers/view/" + teacher.id}>
                      {teacher.fName + " " + teacher.lName}
                    </Link>
                  </TableCell>

                  <TableCell>
                    
                    {(teacher.subjectsList && teacher.subjectsList.length > 0)
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

                  <TableCell>
                    {teacher.teacherShiftDto
                      ? teacher.teacherShiftDto.name
                      : "** Nepriskirta"}
                  </TableCell>
                </TableRow>
              ))}
              
{/* //////////////////////////// */}
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
                  labelDisplayedRows={({ from, to, count }) =>
                    `${from}-${to} iš ${count}`
                  }
                  onPageChange={handleChangePage}
                  rowsPerPage={rowsPerPageA}
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
                  <TableCell >Vardas Pavardė</TableCell>
                  <TableCell >Dėstomi dalykai</TableCell>
                  <TableCell className="activity">Veiksmai</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {(rowsPerPageP > 0
                  ? deletedTeachers.slice(
                      page * rowsPerPageP,
                      page * rowsPerPageP + rowsPerPageP
                    )
                  : deletedTeachers
                ).map((teacher) => (
                  <TableRow key={teacher.id}>
                    {/* <TableCell colSpan={6} component="th" scope="row">
                      {teacher.fName}
                    </TableCell>
                    <TableCell colSpan={6}>{teacher.active}</TableCell>
                    <TableCell colSpan={2} align="center"> */}
                    <TableCell component="th" scope="row">
                      {teacher.fName }
                    </TableCell>
                    <TableCell > 
                    {(teacher.subjectsList && teacher.subjectsList.length > 0)
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
                    <TableCell  align="center">

                      <Button 
                       variant="contained"
                      onClick={() => restoreTeacher(teacher)}>
                        Atstatyti
                      </Button>
                    </TableCell>
                  </TableRow>
                ))}

                {emptyRows > 0 && (
                  <TableRow style={{ height: 53 * emptyRows }}>
                    <TableCell colSpan={3} />
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
                    labelDisplayedRows={({ from, to, count }) =>
                      `${from}-${to} iš ${count}`
                    }
                    SelectProps={{ 
                      inputProps: {
                        "aria-label": "Rodyti po",
                      },
                      native: true,
                    }}
                    onPageChange={handleChangePage}
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
