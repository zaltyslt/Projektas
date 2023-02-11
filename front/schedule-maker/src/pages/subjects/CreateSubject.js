import { Label } from "@mui/icons-material";
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
import { useState } from "react";
import { Link } from "react-router-dom";

export function CreateSubject() {
  const [name, setName] = useState("");
  const [module, setModule] = useState("");
  const [description, setDescription] = useState("");
  const [room, setRoom] = useState([]);

  const clear = () => {
    setName("");
    setDescription("");
    setModule("");
    setRoom([]);
  };

  const handleRoomInput = (event) => {
    const {
      target: { value },
    } = event;
    setRoom(typeof value === "string" ? value.split(",") : value);
  };

  const createSubject = () => {
    fetch("/api/v1/subjects", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        name,
        description,
        module,
        room,
      }),
    }).then(clear);
  };

  return (
    <Container>
      <h3>Pridėti naują dalyką</h3>
      <form>
        <Grid container rowSpacing={2}>
          <Grid item lg={10}>
            <TextField
              fullWidth
              variant="outlined"
              label="Dalyko pavadinimas"
              id="name"
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
                onChange={(e) => setModule(e.target.value)}
              >
                <MenuItem value="Pirmas modulis">Pirmas modulis</MenuItem>
                <MenuItem value="Antras modulis">Antras modulis</MenuItem>
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
                value={room}
                onChange={handleRoomInput}
                input={<OutlinedInput label="Klasės" />}
              ></Select>
            </FormControl>
          </Grid>

          <Grid item lg={10}>
            <Stack direction="row" spacing={2}>
              <Button variant="contained" onClick={createSubject}>
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
