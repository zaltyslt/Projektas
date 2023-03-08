import { useEffect, useState, React } from "react";
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
import Stack from "@mui/material/Stack";

export function ScheduleList() {
  const [schedules, setSchedules] = useState([]);
  const [filter, setFilter] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const [currentPage2, setCurrentPage2] = useState(1);
  const [schedulesPerPage, setSchedulesPerPage] = useState(10);
  const [schedulesPerPage2, setSchedulesPerPage2] = useState(10);
  const paginate = (pageNumber) => setCurrentPage(pageNumber);
  const paginate2 = (pageNumber2) => setCurrentPage2(pageNumber2);
  const [isChecked, setChecked] = useState(false);

  const fetchSchedules = () => {
    fetch("api/v1/schedules")
      .then((responce) => responce.json())
      .then((jsonResponce) => setSchedules(jsonResponce));
  };

  useEffect(() => {
    fetchSchedules();
  }, []);

  const filteredSchedules = schedules.filter((schedule) => {
    return String(schedule.groups.name)
        .toLowerCase()
        .includes(filter.toLowerCase())
        && schedule.active === true;
});

const indexOfLastSchedule = currentPage * schedulesPerPage;
const indexOfFirstSchedule = indexOfLastSchedule - schedulesPerPage;
const currentSchedules = filteredSchedules.slice(
    indexOfFirstSchedule,
    indexOfLastSchedule
);

const pageNumbers = [];
for (
    let i = 1;
    i <= Math.ceil(filteredSchedules.length / schedulesPerPage);
    i++
) {
    pageNumbers.push(i);
}

const handlePageChange = (
  event: React.MouseEvent<HTMLButtonElement> | null,
  newPage: number
) => {
  setCurrentPage(newPage);
};
const handleRowsPerPageChange = (
  event: React.ChangeEvent<HTMLInputElement>
) => {
  setSchedulesPerPage(Number(event.target.value));
  setCurrentPage(1);
};


  return (
    <div>
      <Container maxWidth="lg">
        <Grid container rowSpacing={3}>
          <Grid item sm={10}>
            <h3>Tvarkaraščių sąrašas</h3>
          </Grid>
          <Grid item sm={2}>
            <Stack direction="row" justifyContent="flex-end">
              <Link to="/create-schedule">
                <Button variant="contained">Pridėti naują</Button>
              </Link>
            </Stack>
          </Grid>
        </Grid>

        <Grid item sm={12}>
            <Grid container spacing={2}>
              <Grid item sm={8}>
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
              <Grid item sm={4}>
              <TextField
                  fullWidth
                  variant="outlined"
                  name="search-form"
                  label="Paieška"
                  id="search-form"
                  // value={filter}
                  // onChange={(e) => setFilter(e.target.value)}
                ></TextField>
              </Grid>
            </Grid>
          </Grid>


          <TableContainer component={Paper}>
          <Table aria-label="custom pagination table">
            <TableHead>
              <TableRow>
                <TableCell>Grupės pavadinimas</TableCell>
                <TableCell>Tvarkaraštis</TableCell>
                <TableCell className="empty-activity"></TableCell>
              </TableRow>
            </TableHead>

            <TableBody>
              {filteredSchedules
                .slice(
                  (currentPage - 1) * schedulesPerPage,
                  currentPage * schedulesPerPage
                )
                .map((schedule) => (
                  <TableRow key={schedule.id}>
                    <TableCell component="th" scope="row">
                        {schedule.groups.name}
                    </TableCell>
                    <TableCell>
                    <Link to={`/schedules/add-lessons/${schedule.id}`}>
                    {schedule.schoolYear} m. {schedule.semester} pusmetis
                      </Link></TableCell>
                    <TableCell></TableCell>
                  </TableRow>
                ))}
            </TableBody>
            {/* <TableFooter>
              <TableRow>
                <TablePagination
                  labelRowsPerPage="Rodyti po"
                  rowsPerPageOptions={[10, 20, { label: "Visi", value: filteredClassrooms.length }]}
                  labelDisplayedRows={({ from, to, count }) =>
                    `${from}-${to} iš ${count}`
                  }
                  count={filteredClassrooms.length}
                  page={currentPage - 1}
                  rowsPerPage={classroomsPerPage}
                  onPageChange={(_, page) => setCurrentPage(page + 1)}
                  onRowsPerPageChange={(e) =>
                    setClassroomsPerPage(parseInt(e.target.value))
                  }
                />
              </TableRow>
            </TableFooter> */}
          </Table>
        </TableContainer>

      </Container>
    </div>
  )
}