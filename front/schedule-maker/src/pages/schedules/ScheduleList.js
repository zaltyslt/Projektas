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
import { DatePicker, LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";

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
  const [date, setDate] = useState("");

  const fetchSchedules = () => {
    fetch("api/v1/schedules")
      .then((responce) => responce.json())
      .then((jsonResponce) => setSchedules(jsonResponce));
  };

  const enableSchedule = (event, schedule) => {
    fetch(`api/v1/schedules/enable-schedule/${schedule.id}`, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
      },
    }).then(fetchSchedules);
  };

  useEffect(() => {
    fetchSchedules();
  }, []);

  const filteredSchedules = schedules.filter((schedule) => {
    const groupMatches = String(schedule.groups.name)
      .toLowerCase()
      .includes(filter.toLowerCase());
    const isActive = schedule.active === true;
    const isWithinDateRange = date
      ? new Date(schedule.dateFrom) <= date &&
      new Date(schedule.dateUntil) >= date
      : true;
    return groupMatches && isActive && isWithinDateRange;
  });

  const filteredDisabledSchedules = schedules.filter((schedule) => {
    const groupMatches = String(schedule.groups.name)
      .toLowerCase()
      .includes(filter.toLowerCase());
    const isActive = schedule.active === false;
    const isWithinDateRange = date
      ? new Date(schedule.dateFrom) <= date &&
      new Date(schedule.dateUntil) >= date
      : true;
    return groupMatches && isActive && isWithinDateRange;
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

  const indexOfLastSchedule2 = currentPage2 * schedulesPerPage2;
  const indexOfFirstSchedule2 = indexOfLastSchedule2 - schedulesPerPage2;
  const currentSchedule2 = filteredDisabledSchedules.slice(
    indexOfFirstSchedule2,
    indexOfLastSchedule2
  );

  const pageNumbers2 = [];
  for (
    let i = 1;
    i <= Math.ceil(filteredDisabledSchedules.length / schedulesPerPage2);
    i++
  ) {
    pageNumbers2.push(i);
  }

  const handleChange = (newValue) => {
    setDate(newValue);
  };

  

  return (
    <div>
      <Container maxWidth="lg">
        <Grid container rowSpacing={3}>
          <Grid item sm={10}>
            <h3>Tvarkaraščių sąrašas</h3>
          </Grid>
          <Grid item sm={2}>
            <Stack direction="row" justifyContent="flex-end" marginBottom={4} >
              <Link to="/create-schedule">
                <Button id="create-new-schedule" variant="contained">Pridėti naują</Button>
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
              <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DatePicker
                  fullWidth
                  className="DatePicker"
                  variant="outlined"
                  label="Date"
                  format="YYYY/MM/DD"
                  id="date-form"
                  name="date-form"
                  value={date}
                  onChange={handleChange}
                ></DatePicker>
              </LocalizationProvider>
            </Grid>
          </Grid>
        </Grid>

        <TableContainer component={Paper}>
          <Table aria-label="custom pagination table">
            <TableHead>
              <TableRow>
                <TableCell style={{ width: "550px" }}>Grupės pavadinimas</TableCell>
                <TableCell style={{ width: "550px" }}>Tvarkaraštis</TableCell>
                <TableCell style={{ width: "100px" }}></TableCell>
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
                      {schedule.groups ? (
                        !schedule.groups.isActive ? (
                          <span className="Deleted">
                            {schedule.groups.name}
                          </span>
                        ) : (
                          <span>
                            {schedule.groups.name}   {schedule.groups.shift.name}
                          </span>
                        )
                      ) : (
                        <span>Nenurodytas</span>
                      )}
                    </TableCell>

                    <TableCell>
                      <Link to={`/schedules/${schedule.id}`}>
                        {schedule.schoolYear} m. {schedule.semester}
                      </Link>
                    </TableCell>
                    <TableCell className="action" align="center">
                      <Link to={`/planning/${schedule.id}`}>
                        <Button variant="contained">Planuoti</Button>
                      </Link>
                    </TableCell>
                  </TableRow>
                ))}

            </TableBody>
            <TableFooter>
              <TableRow>
                <TablePagination
                  labelRowsPerPage="Rodyti po"
                  rowsPerPageOptions={[
                    10,
                    20,
                    { label: "Visi", value: filteredSchedules.length },
                  ]}
                  labelDisplayedRows={({ from, to, count }) =>
                    `${from}-${to} iš ${count}`
                  }
                  count={filteredSchedules.length}
                  page={currentPage - 1}
                  rowsPerPage={schedulesPerPage}
                  onPageChange={(_, page) => setCurrentPage(page + 1)}
                  onRowsPerPageChange={(e) =>
                    setSchedulesPerPage(parseInt(e.target.value))
                  }
                />
              </TableRow>
            </TableFooter>
          </Table>
        </TableContainer>

        <FormGroup>
          <FormControlLabel
            control={<Checkbox />}
            label="Ištrinti tvarkaraščiai"
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
                  <TableCell>Grupės pavadinimas</TableCell>
                  <TableCell className="activity"></TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {filteredDisabledSchedules
                  .slice(
                    (currentPage2 - 1) * schedulesPerPage2,
                    currentPage2 * schedulesPerPage2
                  )
                  .map((schedule) => (
                    <TableRow key={schedule.id}>
                      <TableCell component="th" scope="row">
                        {schedule.groups.name}
                      </TableCell>
                      <TableCell>
                        <Button
                          variant="contained"
                          data-value="true"
                          value={schedule}
                          onClick={(e) => {
                            enableSchedule(e, schedule);
                          }}
                        >
                          Atstatyti
                        </Button>
                      </TableCell>
                    </TableRow>
                  ))}
              </TableBody>
              <TableFooter>
                <TableRow>
                  <TablePagination
                    labelRowsPerPage="Rodyti po"
                    rowsPerPageOptions={[
                      10,
                      20,
                      {
                        label: "Visi",
                        value: filteredDisabledSchedules.length,
                      },
                    ]}
                    labelDisplayedRows={({ from, to, count }) =>
                      `${from}-${to} iš ${count}`
                    }
                    count={filteredDisabledSchedules.length}
                    page={currentPage2 - schedulesPerPage2}
                    onPageChange={(_, page) => setCurrentPage2(page + 1)}
                    onRowsPerPageChange={(e) =>
                      setSchedulesPerPage2(parseInt(e.target.value))
                    }
                  />
                </TableRow>
              </TableFooter>
            </Table>
          </TableContainer>
        )}
      </Container>
    </div>
  );
}
