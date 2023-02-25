import { useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  Button,
  Container,
  FormControl,
  Grid,
  Select,
  Stack,
  TextField,
  InputLabel,
  MenuItem,
  colors,
  Alert,
  AlertTitle
} from "@mui/material";
import { SelectChangeEvent } from "@mui/material/Select";

export function CreateProgram(props) {
    const [programName, setProgramName] = useState("");
    const [description, setDescription] = useState("");
    const [error, setError] = useState();
    const [success, setSuccess] = useState();
    const [active, setActive] = useState(true);
    const invalidSymbols = "!@#$%^&*_+={}<>|~`\\\"'";
    let navigate = useNavigate();
    const [formValid, setFormValid] = useState(false);

    const clear = () => {
        setProgramName("");
        setDescription("");
      };
    
      const applyResult = (result) => {
        if (result.ok) {
          setSuccess("Sėkmingai pridėta!");
          clear();
        } else {
          setError("Nepavyko sukurti!");
        }
      };

      const createProgram = () => {
        setError("");
        setSuccess("");
        if (!programName) {
          setError("Prašome užpildyti programos pavadinimą.");
        } else if (
            programName.split("").some((char) => invalidSymbols.includes(char))
        ) {
          setError("Programos pavadinimas turi neleidžiamų simbolių.");
        } else if (!description) {
          setError("Prašome užpildyti programos aprašą.");
        } else if (
          description.split("").some((char) => invalidSymbols.includes(char))
        ) {
          setError("Programos aprašas turi neleidžiamų simbolių.");
        } else {
          fetch("/api/v1/programs/create-program", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              programName,
              description,
              active,
            }),
          }).then(applyResult);
        }
      };

      const handleChange = (event: SelectChangeEvent) => {
        setBuilding(event.target.value);
      };
    
    
    return (
        <div>
    <Container>
      <h3>Pridėti naują programą</h3>
      <form>
        <Grid container rowSpacing={2}>
          <Grid item sm={10}>
            <TextField
              fullWidth
              required
              error={formValid}
              helperText={formValid && 'Programos pavadinimas yra privalomas.'}
              id="programName"
              label="Programos pavadinimas"
              value={programName}
              onChange={(e) => setProgramName(e.target.value)}
            ></TextField>
          </Grid>
          <Grid item sm={10}>
            <TextField
              fullWidth
              multiline
              required
              error={formValid}
              helperText={formValid && 'Programos aprašas yra privalomas.'}
              label="Programos aprašas"
              id="description"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
            ></TextField>
          </Grid>
          <Grid item sm={10}>
            {error && (
                <Alert severity="warning">
                {error}
              </Alert>
            )}
            {success && (
                <Alert severity="success">
                {success}
              </Alert>
            )}
            <Stack direction="row" spacing={2}>
              <Button variant="contained" onClick={createProgram}>
                Sukurti
              </Button>
              <Button variant="contained" onClick={() => navigate(-1)}>
                Grįžti
              </Button>
            </Stack>
          </Grid>
        </Grid>
      </form>
    </Container>
        </div>
    )
}