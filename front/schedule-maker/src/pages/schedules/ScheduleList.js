import { useEffect, useState, React, forwardRef } from "react";
import { Link } from "react-router-dom";
import {
  Alert,
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
import DeleteIcon from "@mui/icons-material/Delete";
import { Container } from "@mui/system";
import { SelectChangeEvent } from "@mui/material/Select";
import Stack from "@mui/material/Stack";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import Slide from "@mui/material/Slide";
import { DatePicker, LocalizationProvider } from "@mui/x-date-pickers";

const Transition = forwardRef(function Transition(props, ref) {
  return <Slide direction="up" ref={ref} {...props} />;
});
export function ScheduleList() {
  const [schedules, setSchedules] = useState([]);
  const [filter, setFilter] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const [schedulesPerPage, setSchedulesPerPage] = useState(10);
  const [date, setDate] = useState(null);
  const [open, setOpen] = useState(false);
  const [idToDelete, setidToDelete] = useState("");
  const [createMessage, setCreateMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleClickOpen = (subjectId) => {
    setidToDelete(subjectId);
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleDelete = () => {
    console.log(idToDelete);
    fetch(`api/v1/schedules/delete-schedule/${idToDelete}`, {
      method: "Delete",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => {
        clearMessages();
        if (response.ok) {
          setCreateMessage("Tvarkaraštis ištrintas.");
        } else {
          setErrorMessage(`Tvarkaraščio ištrinti nepavyko.`);
        }
      })

      .then(fetchSchedules);
    setidToDelete("");
    setOpen(false);
  };

  const fetchSchedules = () => {
    fetch("api/v1/schedules")
      .then((response) => response.json())
      .then((jsonResponse) => {
        setSchedules(jsonResponse);
      });
  };

  useEffect(() => {
    fetchSchedules();
  }, []);

  const clearMessages = () => {
    setCreateMessage("");
    setErrorMessage("");
  };

  const filteredSchedules = schedules.filter((schedule) => {
    const groupMatches = String(schedule.groups.name)
      .toLowerCase()
      .includes(filter.toLowerCase());

    const shiftMaches = String(schedule.groups.shift.name)
      .toLowerCase()
      .includes(filter.toLowerCase());

    const schoolYearMatches = String(schedule.schoolYear)
      .toLowerCase()
      .includes(filter.toLowerCase());

    const semesterMatches = String(schedule.semester)
      .toLowerCase()
      .includes(filter.toLowerCase());

    const isWithinDateRange = date
      ? new Date(schedule.dateFrom) <= date &&
        new Date(schedule.dateUntil) >= date
      : true;
     
    return (
      (groupMatches || shiftMaches || schoolYearMatches || semesterMatches) &&
      isWithinDateRange
    );
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

  const handleChange = (newValue) => {
 
    isNaN(newValue) ? setDate(null) : setDate(newValue);
   
    clearMessages();
  };

  useEffect(() => {
    setCurrentPage(1); // reset to first page
  }, [filter, date]);

  return (
    <div>
      <Container maxWidth="lg">
        <Dialog
          open={open}
          TransitionComponent={Transition}
          keepMounted
          onClose={handleClose}
          aria-describedby="alert-dialog-slide-description"
        >
          <DialogTitle>{"Ar tikrai norite ištrinti tvarkaraštį?"}</DialogTitle>
          <DialogContent>
            <DialogContentText id="alert-dialog-slide-description">
              Ištrynus tvarkaraštį {idToDelete}, visi su juo susieti resursai
              bus atlaisvinti, o duomenys pašalinti negrįžtamai.
            </DialogContentText>
          </DialogContent>
          <DialogActions>
            <Button onClick={handleClose}>Atšaukti</Button>
            <Button onClick={handleDelete}>Ištrinti</Button>
          </DialogActions>
        </Dialog>

        <Grid container rowSpacing={3}>
          <Grid item sm={10}>
            <h3>Tvarkaraščių sąrašas</h3>
          </Grid>
          <Grid item sm={2}>
            <Stack direction="row" justifyContent="flex-end" marginBottom={2}>
              <Link to="/create-schedule">
                <Button id="create-new-schedule" variant="contained">
                  Pridėti naują
                </Button>
              </Link>
             
            </Stack>
          </Grid>
        </Grid>

        <Grid item sm={12}>
          <Grid container spacing={2}>
            <Grid item sm={12}>
              {errorMessage && <Alert severity="warning">{errorMessage}</Alert>}
              {createMessage && (
                <Alert severity="success">{createMessage}</Alert>
              )}
            </Grid>

            <Grid item sm={8}>
              <TextField
                fullWidth
                variant="outlined"
                name="search-form"
                label="Paieška"
                id="search-form"
                value={filter}
                onChange={(e) => setFilter(e.target.value)}
                onFocus={clearMessages}
              ></TextField>
            </Grid>

            <Grid item sm={4}>
              <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DatePicker
                  fullWidth
                  className="DatePicker"
                  variant="outlined"
                  label="Data"
                  format="YYYY/MM/DD"
                  id="date-form"
                  name="date-form"
                  value={date || ''}
                  onChange={handleChange}
                  TextFieldComponent={TextField}
                ></DatePicker>
              </LocalizationProvider>
            </Grid>
          </Grid>
        </Grid>

        <TableContainer component={Paper}>
          <Table aria-label="custom pagination table">
            <TableHead>
              <TableRow>
                <TableCell style={{ width: "550px" }}>
                  Grupės pavadinimas
                </TableCell>
                <TableCell style={{ width: "550px" }}>Tvarkaraštis</TableCell>
                <TableCell style={{ width: "550px" }}>Laikotarpis</TableCell>
                <TableCell></TableCell>
                <TableCell></TableCell>
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
                            {schedule.groups.name} {schedule.groups.shift.name}
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
                    <TableCell>
                      <Button
                        id="delete-button-list-schedule"
                        variant="contained"
                        startIcon={<DeleteIcon />}
                        onClick={() => handleClickOpen(schedule.id)}
                      >
                        Ištrinti
                      </Button>
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
      </Container>
    </div>
  );
}
