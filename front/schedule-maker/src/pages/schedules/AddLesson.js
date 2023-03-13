import {
  Alert,
  Button,
  FormControl,
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

export function AddLesson() {
  const [subject, setSubject] = useState({});
  const [classRooms, setClassRooms] = useState([]);
  const [selectedClassRoom, setSelectedClassRoom] = useState("");
  const [teachers, setTeachers] = useState([]);
  const [selectedTeacher, setSelectedTeacher] = useState("");
  const [dateFrom, setDateFrom] = useState("");
  const [plannedHours, setPlannedHours] = useState("");
  const [lessonStartingTime, setLessonStartingTime] = useState("");
  const [lessonEndTime, setLessonEndTime] = useState("");

  const [error, setError] = useState("");
  const [createMessage, setCreateMessage] = useState("");

  const params = useParams();
  const data = useLocation();
  const scheduleId = data.state.schedule.schedule.id;
  const hours = data.state.subject.subject.hours;
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

  useEffect(() => fetchTeachers, []);

  const fetchTeachers = () => {
    fetch(
      `api/v1/teachers/subject?subjectId=${params.id}&shiftId=${shiftId}`,
      {}
    )
      .then((response) => response.json())
      .then(setTeachers);
  };

  const clear = () => {
    fetchTeachers();
    setSelectedClassRoom("");
    setSelectedTeacher("");
    setPlannedHours("");
    setLessonStartingTime("");
    setLessonEndTime("");
    setDateFrom("");
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
        }),
      }
    ).then((response) => {
      let success = response.ok;

      response.json().then((response) => {
        if (!success) {
          setCreateMessage("");
          setError(response.message);
        } else {
          setCreateMessage("Sėkmingai sukurta. ");
          setError("");
          clear();
        }
      });
    });
  };

  const lessons = [
    { value: "1", label: "1 pamoka" },
    { value: "2", label: "2 pamoka" },
    { value: "3", label: "3 pamoka" },
    { value: "4", label: "4 pamoka" },
    { value: "5", label: "5 pamoka" },
    { value: "6", label: "6 pamoka" },
    { value: "7", label: "7 pamoka" },
    { value: "8", label: "8 pamoka" },
    { value: "9", label: "9 pamoka" },
    { value: "10", label: "10 pamoka" },
    { value: "11", label: "11 pamoka" },
    { value: "12", label: "12 pamoka" },
    { value: "13", label: "13 pamoka" },
    { value: "14", label: "14 pamoka" },
  ];

  return (
    <div>
      <Container>
        <h3>{subject.name}</h3>
        <h5>Grupės pamaina: {shift} </h5>
        <form>
          <Grid container rowSpacing={2} spacing={2}>
            <Grid item sm={10}>
              <FormControl fullWidth required>
                <InputLabel id="teacher-label">Mokytojas</InputLabel>
                <Select
                  label="Moktyojas"
                  labelId="teacher-label"
                  id="classroom"
                  value={selectedTeacher}
                  onChange={(e) => {
                    setSelectedTeacher(e.target.value);
                  }}
                >
                  {teachers.map((teacher) => (
                    <MenuItem key={teacher.id} value={teacher}>
                      {teacher.fName} {teacher.lName}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            </Grid>

            <Grid item sm={10}>
              <FormControl fullWidth required>
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
                value={hours}
              ></TextField>
            </Grid>

            <Grid item sm={5}>
              <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DatePicker
                  className="DatePicker"
                  label="Nuo"
                  format="YYYY/MM/DD"
                  value={dateFrom}
                  onChange={(e) => setDateFrom(e)}
                ></DatePicker>
              </LocalizationProvider>
            </Grid>

            <Grid item sm={5}>
              <TextField
                fullWidth
                required
                variant="outlined"
                label="Planuojamų valandų skaičius: "
                id="notPlannedHours"
                name="notPlannedHours"
                value={plannedHours}
                onChange={(e) => setPlannedHours(e.target.value)}
              ></TextField>
            </Grid>

            <Grid item sm={5}>
              <InputLabel id="classroom-label">Pamoka nuo:</InputLabel>
              <Select
                fullWidth
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
            </Grid>

            <Grid item sm={5}>
              <InputLabel id="classroom-label">
                Pamoka iki (imtinai):
              </InputLabel>
              <Select
                fullWidth
                multiline
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
            </Grid>

            <Grid item sm={10}>
              <Stack direction="row" spacing={2}>
                <Button variant="contained" onClick={createLesson}>
                  Suplanuoti
                </Button>
                <Link to={`/schedules/${scheduleId}`}>
                  <Button variant="contained">Atšaukti</Button>
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
