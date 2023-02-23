import {
  Button,
  Container,
  FormControl,
  Grid,
  InputLabel,
  MenuItem,
  OutlinedInput,
  Select,
  Stack,
  TextField,
} from "@mui/material";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

export function CreateSubject() {
  const [name, setName] = useState("");
  const [modules, setModules] = useState([]);
  const [module, setModule] = useState("");
  const [description, setDescription] = useState("");
  const [rooms, setRooms] = useState([]);
  const [formValid, setFormValid] = useState(false);
  const [classRooms, setClassRooms] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    fetch("api/v1/modules")
      .then((response) => response.json())
      .then(setModules);
  }, []);

  useEffect(() => {
    fetch("api/v1/classrooms")
      .then((response) => response.json())
      .then(setRooms);
  }, []);

  const clear = () => {
    setName("");
    setDescription("");
    setModule("");
    setRooms([]);
  };

  const handleRoomInput = (event) => {
    const {
      target: { value },
    } = event;
    setClassRooms(typeof value === "string" ? value.split(",") : value);
  };

  const validation = () => {
    if (name === "") {
      setFormValid(true);
    } else {
      setFormValid(false);
      createSubject();
    }
  };

  const createSubject = () => {
    fetch(`/api/v1/subjects`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        name,
        description,
        module,
        classRooms,
      }),
    }).then((response) => {
      // let statusCode = response.status;
      let success = response.ok;

      response.json().then((response) => {
        if (!success) {
          setError(response.message);
        } else {
          clear();
        }
      });
    });
  };

  return (
    <Container>
      <h3>Pridėti naują dalyką</h3>
      {error && <p>{error}</p>}
      <form>
        <Grid container rowSpacing={2}>
          <Grid item lg={10}>
            <TextField
              fullWidth
              required
              error={formValid}
              helperText={formValid && "Dalyko pavadinimas yra privalomas."}
              variant="outlined"
              label="Dalyko pavadinimas"
              id="name"
              name="name"
              value={name}
              onChange={(e) => setName(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item lg={10}>
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

          <Grid item lg={10}>
            <FormControl fullWidth>
              <InputLabel id="module-label">Modulio pavadinimas</InputLabel>
              <Select
                label="Modulio pavadinimas"
                labelId="module-label"
                id="module"
                value={module}
                onChange={(e) => {
                  setModule(e.target.value);
                }}
              >
                {modules.map((module) => (
                  <MenuItem key={module.id} value={module}>
                    {module.name}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          </Grid>

          <Grid item lg={10}>
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

          <Grid item lg={10}>
            <Stack direction="row" spacing={2}>
              <Button variant="contained" onClick={validation}>
                Išsaugoti
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
