import { Label } from "@mui/icons-material";
import {
  Button,
  Container,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  Stack,
  TextField,
} from "@mui/material";
import { Box } from "@mui/system";
import { useState } from "react";
import { Link } from "react-router-dom";

export function CreateSubject() {
  const [name, setName] = useState("");
  const [module, setModule] = useState({});
  const [description, setDescription] = useState("");

  const clear = () => {
    setName("");
    setDescription("");
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
      }),
    }).then(clear);
  };

  return (
    <div>
      <Container>
        <h3>Pridėti naują dalyką</h3>

        <InputLabel id="name-label">Dalyko pavadinimas</InputLabel>
        <TextField
          fullWidth
          required
          margin="normal"
          label="Dalyko pavadinimas"
          labelId="name-label"
          id="name"
          value={name}
          onChange={(e) => setName(e.target.value)}
        ></TextField>

   
        <InputLabel id="module-label">Modulio pavadinimas</InputLabel>
        <Select
          fullWidth
          labelId="module-label"
          id="module"
          value={module}
          label="Modulio pavadinimas"
          // onChange={handleChange}
        >
          <MenuItem value={10}>Ten</MenuItem>
          <MenuItem value={20}>Twenty</MenuItem>
          <MenuItem value={30}>Thirty</MenuItem>
        </Select>
        

        <InputLabel id="description-label">Dalyko aprašas</InputLabel>
        <TextField
          fullWidth
          multiline
          margin="normal"
          label="Dalyko aprašas"
          labelId="description-label"
          id="description"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
        ></TextField>

        <Stack direction="row" spacing={2}>
          <Button variant="contained" onClick={createSubject}>
            Išsaugoti
          </Button>

          <Link to="/subjects">
            <Button variant="contained">Grįžti</Button>
          </Link>
        </Stack>
      </Container>
    </div>
  );
}
