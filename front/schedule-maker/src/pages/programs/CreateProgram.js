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
    const [errorEmptyName, setErrorEmptyName] = useState(false);
    const [errorSymbolsName, setErrorSymbolsName] = useState(false);
    const [errorEmptyDesc, setErrorEmptyDesc] = useState(false);
    const [errorSymbolsDesc, setErrorSymbolsDesc] = useState(false);

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
        setErrorEmptyName(false);
        setErrorSymbolsName(false);
        setErrorEmptyDesc(false);
        setErrorSymbolsDesc(false);
        if (!programName) {
          setErrorEmptyName(true);
        } else if (
            programName.split("").some((char) => invalidSymbols.includes(char))
        ) {
          setErrorSymbolsName(true);
        } else if (!description) {
          setErrorEmptyDesc(true);
        } else if (
          description.split("").some((char) => invalidSymbols.includes(char))
        ) {
          setErrorSymbolsDesc(true);
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
      <h3 className="create-header">Pridėti naują programą</h3>
      <form>
        <Grid container rowSpacing={2}>
          <Grid item sm={10}>
            <TextField
              fullWidth
              required
              error={errorEmptyName || errorSymbolsName}
              helperText={errorEmptyName ? "Programos pavadinimas yra privalomas."
                : errorSymbolsName
                  ? "Programos pavadinimas turi neleidžiamų simbolių."
                  : ""}
              variant="outlined"
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
              error={errorEmptyDesc || errorSymbolsDesc}
              helperText={
                errorEmptyDesc
                  ? "Programos aprašas yra privalomas."
                  : errorSymbolsDesc
                    ? "Programos aprašas turi neleidžiamų simbolių."
                    : ""}
              variant="outlined"
              label="Programos aprašas"
              id="description"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
            ></TextField>
          </Grid>
          
          <Grid item sm={10}>
            <Stack direction="row" spacing={2}>
              <Button variant="contained" onClick={createProgram}>
                Išsaugoti
              </Button>
              <Button variant="contained" onClick={() => navigate(-1)}>
                Grįžti
              </Button>
            </Stack>  {error && (
                <Alert severity="warning">
                {error}
              </Alert>
            )}
            </Grid>
            <Grid item sm={10}>
            {success && (
                <Alert severity="success">
                {success}
              </Alert>
            )}
          </Grid>
        </Grid>
      </form>
    </Container>
        </div>
    )
}