import { Button, FormControl, Grid, InputLabel, MenuItem, OutlinedInput, Select, TextField } from "@mui/material";
import { Container, Stack } from "@mui/system";
import { useState, useEffect } from "react";
import { Link, useHref, useParams } from "react-router-dom";

export function EditModule() {
    const [module, setModule] = useState({
        number: "",
        name: "", 
    });
    const params = useParams();
    const listUrl = useHref('/modules');

    useEffect(() => {
        fetch("api/v1/modules/" + params.id)
          .then((response) => response.json())
          .then(setModule);
      }, []);

    const deleteModule = (id) => {
        fetch("/api/v1/modules/" + id, {
            method: "DELETE", 
            headers: {
                "Content-Type": "application/json"
            } 
        })
        .then(() => window.location = listUrl);
    };

  return (
    <Container>
      <h1>Redagavimas</h1>
      <h3>{module.name}</h3>
      <form>
        <Grid container rowSpacing={2}>
          <Grid item lg={10}>
            <TextField
              fullWidth
              variant="outlined"
              label="Modulio kodas"
              id="number"
              value={module.number}
              onChange={(e) => setNumber(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item lg={10}>
            <TextField
              fullWidth
              multiline
              variant="outlined"
              label="Modulio pavadinimas"
              id="name"
              value={module.name}
              onChange={(e) => setName(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item lg={10}>
            <Stack direction="row" spacing={2}>
              <Button variant="contained">
                Išsaugoti
              </Button>

              <Button variant="contained" onClick={() => deleteModule(module.id)}>
                Ištrinti
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