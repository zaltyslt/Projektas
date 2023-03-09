import {
  FormControl,
  Grid,
  InputLabel,
  MenuItem,
  Select,
  TextField,
} from "@mui/material";
import { Container } from "@mui/system";
import { useEffect, useState } from "react";
import { useLocation, useParams } from "react-router-dom";

export function AddLesson() {
  const [subject, setSubject] = useState({});
  const [classRooms, setClassRooms] = useState([]);
  const [selectedClassRoom, setSelectedClassRoom] = useState("");
  const [teachers, setTeachers] = useState([]);
  const [selectedTeacher, setSelectedTeacher] = useState("");

  const params = useParams();
  const data = useLocation();
  const hours = data.state.subject.subject.hours;
  const shiftId = data.state.schedule.schedule.groups.shift.id;


  useEffect(() => {
    fetch(`api/v1/subjects/${params.id}`)
      .then((response) => response.json())
      .then((data) => {
        setSubject(data);
        setClassRooms(data.classRooms);
      });
  }, []);

  useEffect(() => {
    fetch(`api/v1/teachers/subject?subjectId=${params.id}&shiftId=${shiftId}`)
    .then((response) => response.json())
    .then(setTeachers);
  }, []);

  return (
    <div>
      <Container>
        <h3>{subject.name}</h3>
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
                <InputLabel id="classroom-label">Klasė</InputLabel>
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
                variant="outlined"
                label="Valandų skaičius: "
                id="hours"
                name="hours"
                value={hours}
              ></TextField>
            </Grid>
            
            <Grid item sm={10}>
              <TextField
                fullWidth
                variant="outlined"
                label="Nesuplanuota valandų: "
                id="notPlannedHours"
                name="notPlannedHours"
                value={hours}
              ></TextField>
            </Grid>

          </Grid>
        </form>
      </Container>
    </div>
  );
}
