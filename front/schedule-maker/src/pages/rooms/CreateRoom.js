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
} from "@mui/material";
import { SelectChangeEvent } from "@mui/material/Select";

export function CreateRoom(props) {
  const [classroomName, setClassroomName] = useState("");
  const [building, setBuilding] = useState("AKADEMIJA");
  const [description, setDescription] = useState("");

  let navigate = useNavigate();

  const clear = () => {
    setClassroomName("");
    setBuilding("");
    setDescription("");
  };

  const applyResult = (result) => {
    if (result.ok) {
      clear();
      window.alert("Sėkmingai pridėta");
    } else {
      window.alert("Nepavyko sukurti: " + result.status);
    }
  };

  const createClassroom = () => {
    fetch("/api/v1/classrooms/create", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        classroomName,
        description,
        building,
      }),
    }).then(applyResult);
  };

  const handleChange = (event: SelectChangeEvent) => {
    setBuilding(event.target.value);
  };

  return (
    <Container>
      <h3>Pridėti naują klasę</h3>
      <form>
        <Grid container rowSpacing={2}>
          <Grid item lg={10}>
            {/* <label htmlFor="building">Pastatas</label> */}
            <FormControl fullWidth>
              <InputLabel id="building-label">Pastatas</InputLabel>
              <Select
                labelId="building-label"
                id="building"
                label="Pastatas"
                value={building}
                // onChange={(e) => setBuilding(e.target.value)}>
                // <option value="AKADEMIJA">AKADEMIJA</option>
                // <option value="TECHIN">TECHIN</option>
                onChange={handleChange}
              >
                <MenuItem value="AKADEMIJA">AKADEMIJA</MenuItem>
                <MenuItem value="TECHIN">TECHIN</MenuItem>
              </Select>
            </FormControl>
          </Grid>

          <Grid item lg={10}>
            <TextField
              fullWidth
              required
              id="classroomName"
              label="Klasės pavadinimas"
              value={classroomName}
              onChange={(e) => setClassroomName(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item lg={10}>
            <TextField
              fullWidth
              multiline
              label="Klasės aprašas"
              id="description"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item lg={10}>
            <Stack direction="row" spacing={2}>
              <Button variant="contained" onClick={createClassroom}>
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
  );
}