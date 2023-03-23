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
  AlertTitle,
} from "@mui/material";
import { SelectChangeEvent } from "@mui/material/Select";
import ".././pages.css";

export function CreateRoom(props) {
  const [classroomName, setClassroomName] = useState("");
  const [building, setBuilding] = useState("AKADEMIJA");
  const [description, setDescription] = useState("");
  const [error, setError] = useState();
  const [errorEmptyName, setErrorEmptyName] = useState(false);
  const [errorSymbolsName, setErrorSymbolsName] = useState(false);
  const [errorLengthName, setErrorLengthName] = useState(false);
  const [errorEmptyDesc, setErrorEmptyDesc] = useState(false);
  const [errorSymbolsDesc, setErrorSymbolsDesc] = useState(false);
  const [errorLengthDesc, setErrorLengthDesc] = useState(false);
  const [errorBuilding, setErrorBuilding] = useState(false);
  const [success, setSuccess] = useState();
  const [active, setActive] = useState(true);
  const invalidSymbols = "!@#$%^&*_+={}<>|~`\\\"'";
  let navigate = useNavigate();
  const [formValid, setFormValid] = useState(false);

  const clear = () => {
    setClassroomName("");
    setBuilding("");
    setDescription("");
  };

  const applyResult = (result) => {
    if (result.ok) {
      setSuccess("Sėkmingai sukurta.");
      clear();
    } else {
      result
        .text()
        .then((text) => {
          const response = JSON.parse(text);
          setError(response.message);
        })
        .catch((error) => {
          setError("Klasės sukurti nepavyko: ", error);
        });
    }
  };

  const createClassroom = () => {
    setError("");
    setSuccess("");
    setErrorEmptyName(false);
    setErrorSymbolsName(false);
    setErrorEmptyDesc(false);
    setErrorSymbolsDesc(false);
    setErrorBuilding(false);

    if (
      classroomName === "" &&
      description === "" 
    ) {
      setErrorEmptyName(true);
      setErrorEmptyDesc(true);
    }else if (!classroomName) {
      setErrorEmptyName(true);
    } else if (
      classroomName.split("").some((char) => invalidSymbols.includes(char))
    ) {
      setErrorSymbolsName(true);
    } else if (!description) {
      setErrorEmptyDesc(true);
    } else if (
      description.split("").some((char) => invalidSymbols.includes(char))
    ) {
      setErrorSymbolsDesc(true);
    } else if (!building) {
      setErrorBuilding(true);
    } else {
      fetch("api/v1/classrooms/create-classroom", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          classroomName,
          description,
          building,
          active,
        }),
      }).then(applyResult);
    }
  };

  const handleChange = (event: SelectChangeEvent) => {
    setBuilding(event.target.value);
  };

  return (
    <Container>
      <h3 className="create-header">Pridėti naują klasę</h3>
      <form>
        <Grid container rowSpacing={2}>
          <Grid item sm={8}>
            <FormControl fullWidth required error={errorBuilding}>
              <InputLabel id="building-label">
                {errorBuilding
                  ? "Prašome pasirinkti pastatą."
                  : "Pastatas"}</InputLabel>
              <Select
                required
                variant="outlined"
                labelId="building-label"
                id="building"
                label="Pastatas"
                value={building}
                onChange={handleChange}
              >
                <MenuItem value="AKADEMIJA">AKADEMIJA</MenuItem>
                <MenuItem value="TECHIN">TECHIN</MenuItem>
              </Select>
            </FormControl>
          </Grid>
          <Grid item sm={8}>
            <TextField
              fullWidth
              required
              error={errorEmptyName || errorSymbolsName || errorLengthName }
              helperText={
                errorEmptyName
                  ? "Klasės pavadinimas yra privalomas."
                  : errorSymbolsName
                    ? "Klasės pavadinimas turi neleidžiamų simbolių." 
                    : errorLengthName
                    ? "Klasės pavadinimas negali būti ilgesnis nei 200 simbolių"
                    : ""
              }
              variant="outlined"
              id="classroomName"
              label="Klasės pavadinimas"
              value={classroomName}
              onChange={(e) => {
                const input = e.target.value;
                if (input.length > 200) {
                  setErrorLengthName(true);
                } else {
                  setErrorLengthName(false);
                }
                setClassroomName(input);
              }}
              // onChange={(e) => setClassroomName(e.target.value)}
            />
          </Grid>
          <Grid item sm={8}>
            <TextField
              fullWidth
              multiline
              required
              error={errorEmptyDesc || errorSymbolsDesc || errorLengthDesc}
              helperText={
                errorEmptyDesc
                  ? "Klasės aprašas yra privalomas."
                  : errorSymbolsDesc
                    ? "Klasės aprašas turi neleidžiamų simbolių."
                    : errorLengthDesc
                    ? "Klasės aprašas negali būti ilgesnis nei 2000 simbolių"
                    : ""
              }
              variant="outlined"
              label="Klasės aprašas"
              id="description"
              value={description}
              onChange={(e) => {
                const input = e.target.value;
                if (input.length > 2000) {
                  setErrorLengthDesc(true);
                } else {
                  setErrorLengthDesc(false);
                }
                setDescription(input);
              }}
              // onChange={(e) => setDescription(e.target.value)}
            ></TextField>
          </Grid>
          <Grid item sm={8} marginTop={2}>
            <Stack direction="row" spacing={2}>
              <Button variant="contained" onClick={createClassroom}>
                Išsaugoti
              </Button>
              <Button variant="contained" onClick={() => navigate(-1)}>
                Grįžti
              </Button>
            </Stack>
          </Grid>
          <Grid item sm={8}>
            {error && <Alert severity="warning">{error}</Alert>}
            {success && <Alert severity="success">{success}</Alert>}
          </Grid>
        </Grid>
      </form>
    </Container>
  );
}
