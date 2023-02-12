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

export function CreateModule() {
  const [number, setNumber] = useState("");
  const [name, setName] = useState("");

  const clear = () => {
    setNumber("");
    setName("");
  };

  const createModule = () => {
    fetch("/api/v1/modules", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        number,
        name,
      }),
    }).then(clear);
  };

  return (
    <Container>
      <h3>Pridėti naują modulį</h3>
      <form>
        <Grid container rowSpacing={2}>

        <Grid item lg={10}>
            <TextField
              fullWidth
              variant="outlined"
              label="Modulio kodas"
              id="number"
              value={number}
              onChange={(e) => setNumber(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item lg={10}>
            <TextField
              fullWidth
              variant="outlined"
              label="Modulio pavadinimas"
              id="name"
              value={name}
              onChange={(e) => setName(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item lg={10}>
            <Stack direction="row" spacing={2}>
              <Button variant="contained" onClick={createModule}>
                Išsaugoti
              </Button>

              <Link to="/modules">
                <Button variant="contained">Grįžti</Button>
              </Link>
            </Stack>
          </Grid>
        </Grid>
      </form>
    </Container>
  );
}