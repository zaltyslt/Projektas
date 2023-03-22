import {
  Button,
  Checkbox,
  FormControl,
  FormControlLabel,
  Grid,
  InputLabel,
  MenuItem,
  Select,
  Stack,
} from "@mui/material";
import { Container } from "@mui/system";
import { useEffect, useState } from "react";
import { Link, useLocation, useParams } from "react-router-dom";

export function EditLesson() {
  const [workDay, setWorkDay] = useState({});
  const [selectedTeacher, setSelectedTeacher] = useState("");
  const [teachers, setTeachers] = useState([]);
  const [subject, setSubject] = useState({});
  const [classRooms, setClassRooms] = useState([]);
  const [selectedClassRoom, setSelectedClassRoom] = useState("");
  const [online, setOnline] = useState(false);

  const params = useParams();

  useEffect(() => {
    fetch(`api/v1/schedules/lesson/${params.id}`)
      .then((response) => response.json())
      .then((data) => {
        setWorkDay(data);
        setSubject(data.subject);
      });
  }, []);

  useEffect(() => {
    fetch("api/v1/teachers/" + subject.id)
      .then((response) => response.json())
      .then(setTeachers);
  }, [subject]);

  useEffect(() => {
    fetch("api/v1/subjects/" + subject.id)
      .then((response) => response.json())
      .then((data) => setClassRooms(data.classRooms));
  }, [subject]);

  const handleCheck = (event) => {
    setOnline(event.target.checked);
  };

  return (
    <div>
      <Container>
        <h1>Redagavimas</h1>
        <h3>{subject.name}</h3>
        <h5>Data: {workDay.date}</h5>
        <h5>
          Pamokų laikas: {workDay.lessonStart} - {workDay.lessonEnd}
        </h5>
        <form>
          <Grid container rowSpacing={2}>
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
              <Stack direction="row" spacing={2}>
                <Button variant="contained">Išsaugoti</Button>
                <Link>
                  <Button variant="contained">Atšaukti</Button>
                </Link>
                <Button variant="contained">Ištrinti</Button>
              </Stack>
            </Grid>
          </Grid>
        </form>
      </Container>
    </div>
  );
}
