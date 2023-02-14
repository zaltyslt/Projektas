import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
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
  FormControl,
  InputLabel,
  Select,
  MenuItem,
} from "@mui/material";
import { Container } from "@mui/system";
import { SelectChangeEvent } from "@mui/material/Select";

export function RoomList() {
  const [classrooms, setClassrooms] = useState([]);
  const [building, setBuilding] = useState("All");
  const [filter, setFilter] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const [classroomsPerPage, setClassroomsPerPage] = useState(10);
  const paginate = (pageNumber) => setCurrentPage(pageNumber);
  const [isChecked, setChecked] = useState(false);

  // const JSON_HEADERS = {
  //   "Content-Type": "application/json",
  // };

  const fetchClassrooms = () => {
    fetch("/api/v1/classrooms")
      .then((responce) => responce.json())
      .then((jsonResponce) => setClassrooms(jsonResponce));
  };

  useEffect(() => {
    fetchClassrooms();
  }, []);

  const filteredClassrooms = classrooms.filter((classroom) => {
    if (building === "All") {
      return String(classroom.classroomName)
        .toLowerCase()
        .includes(filter.toLowerCase());
    } else {
      return (
        String(classroom.classroomName)
          .toLowerCase()
          .includes(filter.toLowerCase()) && classroom.building === building
      );
    }
  });

  // const indexOfLastClassroom = currentPage * classroomsPerPage;
  // const indexOfFirstClassroom = indexOfLastClassroom - classroomsPerPage;
  // const currentClassrooms = filteredClassrooms.slice(
  //   indexOfFirstClassroom,
  //   indexOfLastClassroom
  // );

  const filteredActiveClassrooms = classrooms.filter(
    (classroom) =>
        String(classroom.classroomName).toLowerCase().includes(filter.toLowerCase())
        && classroom.active === true
);

const filteredDisabledClassrooms = classrooms.filter(
  (classroom) =>
      String(classroom.classroomName).toLowerCase().includes(filter.toLowerCase())
      && classroom.active === false
);

const indexOfLastClassroom = currentPage * classroomsPerPage;
const indexOfFirstClassroom = indexOfLastClassroom - classroomsPerPage;
const currentClassrooms = filteredActiveClassrooms.slice(
    indexOfFirstClassroom,
    indexOfLastClassroom
);

const pageNumbers = [];
for (
    let i = 1;
    i <= Math.ceil(filteredActiveClassrooms.length / classroomsPerPage);
    i++
) {
    pageNumbers.push(i);
}

  const handleChange = (event: SelectChangeEvent) => {
    setBuilding(event.target.value);
  };

  return (
    <div>
      <Container maxWidth="lg">
        <Grid container rowSpacing={3}>
          <Grid item lg={10}>
            <h3>Klasių sąrašas</h3>
          </Grid>
          <Grid item lg={2}>
            <Link to="/create">
              <Button variant="contained">Sukurti naują</Button>
            </Link>
          </Grid>

          <Grid container spacing={2}>
            <Grid item xs={8}>
              <TextField
                fullWidth
                variant="outlined"
                name="search-form"
                label="Paieška"
                id="search-form"
                value={filter}
                onChange={(e) => setFilter(e.target.value)}
              ></TextField>
            </Grid>
            <Grid item xs={4}>
              <FormControl fullWidth>
                <InputLabel id="building-label">Pastatas</InputLabel>
                <Select
                  labelId="building-label"
                  id="building"
                  label="Pastatas"
                  value={building}
                  onChange={handleChange}
                >
                  {/* Kaip cia ideti verte, kad vel rodytu viska??? TODO */}
                  <MenuItem value="AKADEMIJA, TECHIN">
                    <em>None</em>
                  </MenuItem>
                  <MenuItem value="AKADEMIJA">AKADEMIJA</MenuItem>
                  <MenuItem value="TECHIN">TECHIN</MenuItem>
                </Select>
              </FormControl>
            </Grid>
          </Grid>
        </Grid>

        <TableContainer component={Paper}>
          <Table aria-label="custom pagination table">
            <TableHead>
              <TableRow>
                <TableCell>Klasės pavadinimas</TableCell>
                <TableCell>Pastatas</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {filteredClassrooms
                .slice(
                  (currentPage - 1) * classroomsPerPage,
                  currentPage * classroomsPerPage
                )
                .map((classroom) => (
                  <TableRow key={classroom.id}>
                    <TableCell component="th" scope="row">
                      <Link to={`/classrooms/view/${classroom.id}`}>
                        {classroom.classroomName}
                      </Link>
                    </TableCell>
                    <TableCell>{classroom.building}</TableCell>
                  </TableRow>
                ))}

              {/* {emptyRows > 0 && (
                <TableRow style={{ height: 53 * emptyRows }}>
                  <TableCell colSpan={6} />
                </TableRow>
              )} */}
            </TableBody>
            <TableFooter>
              <TableRow>
                {/* <TablePagination
                  component="div"
                  count={100}
                  page={page}
                  onPageChange={handleChangePage}
                  rowsPerPage={rowsPerPage}
                  onRowsPerPageChange={handleChangeRowsPerPage}
                /> */}
                {/* <TablePagination
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
                ></TablePagination> */}
              </TableRow>
            </TableFooter>
          </Table>
        </TableContainer>

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
                <input type="checkbox"
                       onChange={(e) => e.target.checked ? setChecked(true) : setChecked(false)}/>
                <label>Ištrintos klasės</label>
            </div>

            <tbody>
            {/*check boxo if'as jeigu true rodo filteredDisabledClassrooms sarasa, 1:1(vienas prie vieno su filteredActiveClassrooms tavo parasyta logika)  */}
            {isChecked && filteredDisabledClassrooms
                .slice(
                    (currentPage - 1) * classroomsPerPage,
                    currentPage * classroomsPerPage
                )
                .map((classroom) => (
                    <tr key={classroom.id}>
                        <td>
                            <Link to={`/classrooms/view/${classroom.id}`}>
                                {classroom.classroomName}
                            </Link>
                        </td>
                        <td>{classroom.building}</td>
                    </tr>
                ))}
            </tbody>
      </Container>
    </div>
  );
}
