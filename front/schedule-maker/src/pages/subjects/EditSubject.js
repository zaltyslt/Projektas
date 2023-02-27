import {
  Button,
  FormControl,
  Grid,
  InputLabel,
  MenuItem,
  OutlinedInput,
  Select,
  TextField,
} from "@mui/material";
import { Container, Stack } from "@mui/system";
import { useState, useEffect } from "react";
import { Link, useHref, useParams } from "react-router-dom";

export function EditSubject() {
  const [subject, setSubject] = useState({
    name: "",
    description: "",
    module: {},
    classRooms: [],
  });
  const params = useParams();
  const [modules, setModules] = useState([]);
  const [module, setModule] = useState("");
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [classRooms, setClassRooms] = useState([]);
  const [rooms, setRooms] = useState([]);
  const listUrl = useHref("/subjects");

  useEffect(() => {
    fetch("api/v1/subjects/" + params.id)
      .then((response) => response.json())
      .then((data) => {
        setSubject(data);
        setDescription(data.description);
        setName(data.name);
      });
  }, []);

  useEffect(() => {
    fetch("api/v1/modules")
      .then((response) => response.json())
      .then(setModules);
  }, []);

  useEffect(() => {
    fetch("api/v1/classrooms/active")
      .then((response) => response.json())
      .then(setRooms);
  }, []);

  const handleRoomInput = (event) => {
    const {
      target: { value },
    } = event;
    setClassRooms(typeof value === "string" ? value.split(",") : value);
  };

  const deleteSubject = (id) => {
    fetch("/api/v1/subjects/delete/" + id, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
      },
    }).then(() => (window.location = listUrl));
  };

  const handleEditSubject = () => {
    fetch(`/api/v1/subjects/${params.id}`, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        name,
        description,
        module,
        classRooms,
      }),
    }).then(() => (window.location = listUrl));
  };

  return (
    <Container>
      <h1>Redagavimas</h1>
      <h3>{subject.name}</h3>
      <form>
        <Grid container rowSpacing={2}>
          <Grid item sm={10}>
            <TextField
              fullWidth
              variant="outlined"
              label="Dalyko pavadinimas"
              id="name"
              value={name}
              onChange={(e) => setName(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item sm={10}>
            <TextField
              fullWidth
              multiline
              variant="outlined"
              label="Dalyko aprašas"
              id="description"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item sm={10}>
            <FormControl fullWidth>
              <InputLabel id="module-label">Modulio pavadinimas</InputLabel>
              <Select
                label="Modulio pavadinimas"
                labelId="module-label"
                id="module"
                value={module}
                onChange={(e) => setModule(e.target.value)}
              >
                {modules.map((module) => (
                  <MenuItem key={module.id} value={module}>
                    {module.name}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          </Grid>

          <Grid item sm={10}>
            <FormControl fullWidth>
              <InputLabel id="room-label">Klasės</InputLabel>
              <Select
                multiple
                labelId="room-label"
                id="room"
                value={classRooms}
                onChange={handleRoomInput}
                input={<OutlinedInput label="Klasės" />}
              >
                {rooms.map((room) => (
                  <MenuItem key={room.id} value={room}>
                    {room.classroomName}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          </Grid>

          <Grid item sm={10}>
            <Stack direction="row" spacing={2}>
              <Button
                variant="contained"
                onClick={() => handleEditSubject(subject.id)}
              >
                Išsaugoti
              </Button>

              <Button
                variant="contained"
                onClick={() => deleteSubject(subject.id)}
              >
                Ištrinti
              </Button>

              <Link to="/subjects">
                <Button variant="contained">Grįžti</Button>
              </Link>
            </Stack>
          </Grid>
        </Grid>
      </form>
    </Container>
  );
}
