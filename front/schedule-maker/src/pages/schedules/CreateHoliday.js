import { Button, Grid, Stack, TextField } from "@mui/material";
import { Container } from "@mui/system";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { DatePicker, LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { dateToUtc } from "../../helpers/helpers";
import "./Schedule.css";

export function CreateHoliday() {
  const [name, setName] = useState("");
  const [dateFrom, setDateFrom] = useState("");
  const [dateUntil, setDateUntil] = useState("");

  const [dateFromEmpty, setDateFromEmpty] = useState(false);
  const [dateUntilEmpty, setDateUntilEmpty] = useState(false);

  const [errorMessageFrom, setErrorMessageFrom] = useState("");
  const [errorMessageUntil, setErrorMessageUntil] = useState("");
  const [errorLengthName, setErrorLengthName] = useState(false);
  const [errorLengthYear, setErrorLengthYear] = useState(false);

  const validateDateFrom = (value) => {
    setDateFrom(dateToUtc(value));
    if (value.length === 0) {
      setDateFromEmpty(true);
    } else {
      setDateFromEmpty(false);
      setErrorMessageFrom("");
    }
  };

  const validateDateUntil = (value) => {
    setDateUntil(dateToUtc(value));
    if (value.length === 0) {
      setDateUntilEmpty(true);
    } else {
      setDateUntilEmpty(false);
      setErrorMessageUntil("");
    }
  };

  const validation = () => {

  };

  return (
    <div>
      <Container>
        <h3>Atostogų planavimas</h3>
        <Grid container rowSpacing={2} spacing={2}>
          <Grid item sm={10}>
            <TextField
              fullWidth
              variant="outlined"
              label="Pavadinimas"
              id="name"
              name="name"
              value={name}
            ></TextField>
          </Grid>

          <Grid item sm={5}>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
              <DatePicker
                className="DatePicker"
                label="Nuo"
                format="YYYY/MM/DD"
                value={dateFrom}
                onChange={(e) => validateDateFrom(e)}
                slotProps={{
                  textField: {
                    helperText: errorMessageFrom,
                    error: dateFromEmpty,
                  },
                }}
              ></DatePicker>
            </LocalizationProvider>
          </Grid>

          <Grid item sm={5}>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
              <DatePicker
                className="DatePicker"
                label="Iki"
                format="YYYY/MM/DD"
                value={dateUntil}
                onChange={(e) => validateDateUntil(e)}
                slotProps={{
                  textField: {
                    helperText: errorMessageUntil,
                    error: dateUntilEmpty,
                  },
                }}
              ></DatePicker>
            </LocalizationProvider>
          </Grid>

          <Grid item sm={10}>
            <Stack direction="row" spacing={2}>
              <Button
                id="save-button-create-schedule"
                variant="contained"
                onClick={validation}
              >
                Išsaugoti
              </Button>

              <Link to="/">
                <Button id="back-button-create-schedule" variant="contained">
                  Grįžti
                </Button>
              </Link>
            </Stack>
          </Grid>
        </Grid>
      </Container>
    </div>
  );
}
