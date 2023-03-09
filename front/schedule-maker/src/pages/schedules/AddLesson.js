import { FormControl, Grid, InputLabel, MenuItem, Select } from "@mui/material";
import { Container } from "@mui/system";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

export function AddLesson() {
  const [subject, setSubject] = useState({});
  const [classRooms, setClassRooms] = useState([]);
  const [selectedClassRoom, setSelectedClassRoom] = useState("");
  const params = useParams();

  useEffect(() => {
    fetch(`api/v1/subjects/${params.id}`)
      .then((response) => response.json())
      .then((data) => {
        setSubject(data);
        setClassRooms(data.classRooms);
      });
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
          </Grid>
        </form>
      </Container>
    </div>
  );
}
