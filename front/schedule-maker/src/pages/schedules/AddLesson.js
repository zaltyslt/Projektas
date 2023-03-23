import {
  Alert,
  Button,
  Checkbox,
  FormControl,
  FormControlLabel,
  FormHelperText,
  Grid,
  InputLabel,
  MenuItem,
  Select,
  Stack,
  TextField,
} from "@mui/material";
import { Container } from "@mui/system";
import { DatePicker, LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { useEffect, useState } from "react";
import { Link, useLocation, useParams } from "react-router-dom";
import { lessons } from "../../helpers/constants";
import { dateToUtc } from "../../helpers/helpers";

export function AddLesson() {
  const [subject, setSubject] = useState({});
  const [classRooms, setClassRooms] = useState([]);
  const [selectedClassRoom, setSelectedClassRoom] = useState("");
  const [teachers, setTeachers] = useState([]);
  const [selectedTeacher, setSelectedTeacher] = useState("");
  const [dateFrom, setDateFrom] = useState("");
  const [plannedHours, setPlannedHours] = useState("");
  const [lessonStartingTime, setLessonStartingTime] = useState("1");
  const [lessonEndTime, setLessonEndTime] = useState("1");
  const [online, setOnline] = useState(false);

  const [error, setError] = useState("");
  const [errorMessageFrom, setErrorMessageFrom] = useState("");
  const [createMessage, setCreateMessage] = useState("");
  const [hoursEmpty, setHoursEmpty] = useState(false);
  const [hoursNotValid, setHoursNotValid] = useState(false);
  const [dateFromEmpty, setDateFromEmpty] = useState(false);
  const [shiftStartEmpty, setShiftStartEmpty] = useState(false);
  const [shiftEndEmpty, setShiftEndEmpty] = useState(false);
  const [isValidShiftTime, setIsValidShiftTime] = useState(true);
  const [shiftTooLong, setShiftTooLong] = useState(false);

  const params = useParams();
  const data = useLocation();
  const scheduleId = data.state.schedule.schedule.id;
  const hours = data.state.subject.subject.hours;
  const unplannedHours =
    data.state.subject.subject.id in
    data.state.schedule.schedule.subjectIdWithUnassignedTime
      ? data.state.schedule.schedule.subjectIdWithUnassignedTime[
          data.state.subject.subject.id
        ]
      : data.state.subject.subject.hours;

  const shiftId = data.state.schedule.schedule.groups.shift.id;
  const shift = data.state.schedule.schedule.groups.shift.name;

  useEffect(() => {
    fetch(`api/v1/subjects/${params.id}`)
      .then((response) => response.json())
      .then((data) => {
        setSubject(data);
        setClassRooms(data.classRooms);
      });
  }, []);

  useEffect(() => {
    if (parseInt(lessonStartingTime) > parseInt(lessonEndTime)) {
      setIsValidShiftTime(false);
    } else {
      setIsValidShiftTime(true);
    }
  }, [lessonStartingTime, lessonEndTime]);

  useEffect(() => {
    fetch(
      `api/v1/teachers/subject?subjectId=${params.id}&shiftId=${shiftId}`
    )
      .then((response) => response.json())
      .then(setTeachers);
  }, []);

  const clear = () => {
    setSelectedClassRoom("");
    setSelectedTeacher("");
    setPlannedHours("");
    setLessonStartingTime("1");
    setLessonEndTime("1");
    setDateFrom("");
    setShiftTooLong(false);
  };

  const createLesson = () => {
    fetch(
      `api/v1/schedules/plan-schedule/${scheduleId}?subjectId=${params.id}`,
      {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          classroom: selectedClassRoom,
          teacher: selectedTeacher,
          assignedHours: plannedHours,
          dateFrom,
          startIntEnum: lessonStartingTime,
          endIntEnum: lessonEndTime,
          online,
        }),
      }
    ).then((response) => {
      let success = response.ok;

      response.json().then((response) => {
        if (!success) {
          setCreateMessage("");
          setError(response.message);
        } else {
          setCreateMessage("Sėkmingai įdėta į tvarkaraštį. ");
          setError("");
          clear();
        }
      });
    });
  };

  const handleCheck = (event) => {
    setOnline(event.target.checked);
  };

  const validateHours = (value) => {
    setPlannedHours(value);
    value.length === 0 ? setHoursEmpty(true) : setHoursEmpty(false);

    const isValidHourString = /^[0-9]+$/.test(value);
    isValidHourString ? setHoursNotValid(false) : setHoursNotValid(true);
  };

  const validateDate = (value) => {
    setDateFrom(dateToUtc(value));
    if (value.length === 0) {
      setDateFromEmpty(true);
    } else {
      setDateFromEmpty(false);
      setErrorMessageFrom("");
    }
  };

  const validation = () => {
    let isValid = true;

    if (dateFrom === "" || dateFrom === "Undifined") {
      setDateFromEmpty(true);
      setErrorMessageFrom("Privaloma pasirinkti pradžios datą.");
      isValid = false;
    } else {
      setDateFromEmpty(false);
    }

    if (plannedHours === "" || plannedHours === "undefined") {
      setHoursEmpty(true);
      isValid = false;
    }

    if (lessonStartingTime === "" || lessonStartingTime === "undefined") {
      setShiftStartEmpty(true);
      isValid = false;
    }

    if (lessonEndTime === "" || lessonEndTime === "undefined") {
      setShiftEndEmpty(true);
      isValid = false;
    }

    if (lessonEndTime - lessonStartingTime > 8) {
      setShiftTooLong(true);
      isValid = false;
    }

    if (isValid) {
      createLesson();
    }
  };

  return (
    <div>
      <Container>
        <h3>{subject.name}</h3>
        <h5>Grupės pamaina: {shift} </h5>
        <form>
          <Grid container rowSpacing={2} spacing={2}>
            <Grid item sm={10}>
              <FormControl fullWidth>
                <InputLabel id="teacher-label">Mokytojas</InputLabel>
                <Select
                  label="Mokytojas"
                  labelId="teacher-label"
                  id="classroom"
                  value={selectedTeacher}
                  onChange={(e) => {
                    setSelectedTeacher(e.target.value);
                  }}
                >
                  {teachers.length === 0 && (
                    <MenuItem>
                      Nurodytai pamainai ir dalykui tinkamo mokytojo nerasta
                    </MenuItem>
                  )}
                  {teachers.map((teacher) => (
                    <MenuItem key={teacher.id} value={teacher}>
                      {teacher.fName} {teacher.lName}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            </Grid>

            <Grid item sm={10}>
              <FormControl fullWidth>
                <InputLabel id="classroom-label">Klasės pavadinimas</InputLabel>
                <Select
                  label="Klasės pavadinimas"
                  labelId="classroom-label"
                  id="classroom"
                  value={selectedClassRoom}
                  onChange={(e) => {
                    setSelectedClassRoom(e.target.value);
                  }}
                >
                  {classRooms.map((classroom) => (
                    <MenuItem key={classroom.id} value={classroom}>
                      {classroom.classroomName}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            </Grid>

            <Grid item sm={10}>
              <FormControlLabel
                label="Nuotolinės pamokos"
                control={<Checkbox onChange={handleCheck}></Checkbox>}
              ></FormControlLabel>
            </Grid>

            <Grid item sm={10}>
              <TextField
                fullWidth
                disabled
                variant="outlined"
                label="Dalyko valandų skaičius: "
                id="hours"
                name="hours"
                value={hours}
              ></TextField>
            </Grid>

            <Grid item sm={10}>
              <TextField
                fullWidth
                disabled
                variant="outlined"
                label="Nesuplanuota valandų: "
                id="notPlannedHours"
                name="notPlannedHours"
                value={unplannedHours}
              ></TextField>
            </Grid>

            <Grid item sm={5}>
              <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DatePicker
                  className="DatePicker"
                  label="Nuo"
                  format="YYYY/MM/DD"
                  value={dateFrom}
                  onChange={(e) => validateDate(e)}
                  slotProps={{
                    textField: {
                      helperText: errorMessageFrom,
                      error: dateFromEmpty,
                    },
                  }}
                ></DatePicker>
              </LocalizationProvider>
            </Grid>

            <Grid item sm={5}>
              <TextField
                fullWidth
                required
                error={hoursEmpty || hoursNotValid}
                helperText={
                  hoursEmpty
                    ? "Privaloma pasirinkti planuojamų valandų skaičių"
                    : hoursNotValid
                    ? "Laukas turi susidėti iš skaičių"
                    : ""
                }
                variant="outlined"
                label="Planuojamų valandų skaičius: "
                id="notPlannedHours"
                name="notPlannedHours"
                value={plannedHours}
                onChange={(e) => validateHours(e.target.value)}
              ></TextField>
            </Grid>

            <Grid item sm={5}>
              <InputLabel id="lessons-start-label">Pamoka nuo:</InputLabel>
              <Select
                fullWidth
                error={!isValidShiftTime || shiftStartEmpty}
                variant="outlined"
                label="Pamokų pradžia"
                id="lesson-start"
                value={lessonStartingTime}
                onChange={(e) => setLessonStartingTime(e.target.value)}
              >
                {lessons.map((lesson) => (
                  <MenuItem key={lesson.value} value={lesson.value}>
                    {lesson.label}
                  </MenuItem>
                ))}
              </Select>
              {!isValidShiftTime && (
                <FormHelperText error>
                  Pirma pamoka negali prasidėti vėliau negu paskutinė pamoka.
                </FormHelperText>
              )}
              {shiftStartEmpty && (
                <FormHelperText error>
                  Privaloma pasirinkti pamoką.
                </FormHelperText>
              )}
            </Grid>

            <Grid item sm={5}>
              <InputLabel id="lesson-end-label">
                Pamoka iki (imtinai):
              </InputLabel>
              <Select
                fullWidth
                error={!isValidShiftTime || shiftEndEmpty || shiftTooLong}
                variant="outlined"
                label="Pamokų pabaiga"
                id="lesson-end"
                value={lessonEndTime}
                onChange={(e) => setLessonEndTime(e.target.value)}
              >
                {lessons.map((lesson) => (
                  <MenuItem key={lesson.value} value={lesson.value}>
                    {lesson.label}
                  </MenuItem>
                ))}
              </Select>
              {!isValidShiftTime && (
                <FormHelperText error>
                  Pirma pamoka negali prasidėti vėliau negu paskutinė pamoka.
                </FormHelperText>
              )}
              {shiftEndEmpty && (
                <FormHelperText error>
                  Privaloma pasirinkti pamoką.
                </FormHelperText>
              )}
              {shiftTooLong && (
                <FormHelperText error>
                  Negali būti daugiau 8 pamokų per dieną.
                </FormHelperText>
              )}
            </Grid>

            <Grid item sm={10}>
              <Stack direction="row" spacing={2}>
                <Button variant="contained" onClick={validation}>
                  Suplanuoti
                </Button>
                <Link to={`/planning/${scheduleId}`}>
                  <Button variant="contained">Atšaukti</Button>
                </Link>
                <Link to={"/schedules/" + scheduleId}>
                  <Button variant="contained">Tvarkaraštis</Button>
                </Link>
              </Stack>
            </Grid>

            <Grid item sm={10}>
              {error && <Alert severity="warning">{error}</Alert>}
              {createMessage && (
                <Alert severity="success">{createMessage}</Alert>
              )}
            </Grid>
          </Grid>
        </form>
      </Container>
    </div>
  );
}
