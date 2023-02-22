import { Alert, Button, Container, Grid, Stack, TextField } from "@mui/material";
import { useState } from "react";
import { Link } from "react-router-dom";

export function CreateModule() {
  const [number, setNumber] = useState("");
  const [name, setName] = useState("");
  const [success, setSuccess] = useState("");
  const [error, setError] = useState("");

  const invalidSymbols = "!@#$%^&*_+={}<>|~`\\'";

  const clear = () => {
    setNumber("");
    setName("");
  };

  const validation = () => {
    setError("");
    setSuccess("");
    if (!number) {
      setError("Prašome užpildyti modulio kodą.");
    } else if (number.split("").some((char) => invalidSymbols.includes(char))) {
      setError("Modulio numeris turi neleidžiamų simbolių.")
    } else if (!name) {
      setError("Prašome užpildyti modulio pavadinimą.")
    } else if (name.split("").some((char) => invalidSymbols.includes(char))) {
      setError("Modulio pavadinimas turi neleidžiamų simbolių.");
    } else {
      createModule();
    }
  };

  const applyResult = (result) => {
    if (result.ok) {
      setSuccess("Sėkmingai pridėta");
      clear();
    } else if (result.status === 400) {
      setError("Modulis su tokiu numeriu jau egzistuoja");
    } else {
      setError("Nepavyko pridėti");
    }
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
    }).then(applyResult);
  };

  return (
    <Container>
      <h3>Pridėti naują modulį</h3>
      <form>
        <Grid container rowSpacing={2}>
          <Grid item sm={10}>
            <TextField
              fullWidth
              required
              variant="outlined"
              label="Modulio kodas"
              id="number"
              value={number}
              onChange={(e) => setNumber(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item sm={10}>
            <TextField
              fullWidth
              required
              variant="outlined"
              label="Modulio pavadinimas"
              id="name"
              value={name}
              onChange={(e) => setName(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item sm={10}>
            {error && <Alert severity="warning">{error}</Alert>}
            {success && <Alert severity="success">{success}</Alert>}
            <Stack direction="row" spacing={2}>
              <Button variant="contained" onClick={validation}>
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
