import { Alert, Button, Grid, TextField } from "@mui/material";
import { Container, Stack } from "@mui/system";
import { useState, useEffect } from "react";
import { Link, useHref, useParams } from "react-router-dom";

export function EditModule() {
  const [module, setModule] = useState({});
  const [number, setNumber] = useState("");
  const [name, setName] = useState("");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const listUrl = useHref("/modules");

  const params = useParams({
    number: "",
    name: "",
  });

  const invalidSymbols = "!@#$%^&*_+={}<>|~`\\'";

  useEffect(() => {
    fetch(`api/v1/modules/${params.id}`)
      .then((response) => response.json())
      .then((data) => {
        setModule(data);
        setNumber(data.number);
        setName(data.name);
      });
  }, []);

  const deleteModule = (id) => {
    fetch("/api/v1/modules/" + id, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
    }).then(() => (window.location = listUrl));
  };

  const handleEditModule = () => {
    setError("");
    setSuccess("");
    if (!number) {
      setError("Prašome užpildyti modulio kodą.");
    } else if (number.split("").some((char) => invalidSymbols.includes(char))) {
      setError("Modulio numeris turi neleidžiamų simbolių.");
    } else if (!name) {
      setError("Prašome užpildyti modulio pavadinimą.");
    } else if (name.split("").some((char) => invalidSymbols.includes(char))) {
      setError("Modulio pavadinimas turi neleidžiamų simbolių.");
    } else {
      fetch(`/api/v1/modules/${params.id}`, {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          number,
          name,
        }),
      }).then(applyResult);
    }
  };

  const applyResult = (result) => {
    if (result.ok) {
      setSuccess("Sėkmingai atnaujinote!");
    } else {
      setError("Atnaujinti nepavyko!");
    }
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
              value={number}
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
              value={name}
              onChange={(e) => setName(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item lg={10}>
            {error && <Alert severity="warning">{error}</Alert>}
            {success && <Alert severity="success">{success}</Alert>}
            <Stack direction="row" spacing={2}>
              <Button
                variant="contained"
                onClick={() => handleEditModule(module.id)}
              >
                Išsaugoti
              </Button>

              <Button
                variant="contained"
                onClick={() => deleteModule(module.id)}
              >
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
