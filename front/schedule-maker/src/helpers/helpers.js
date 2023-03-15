import dayjs from "dayjs"

export const dateToUtc = (date) => {
    return dayjs(date.$d.getTime() - (date.$d.getTimezoneOffset() * 60 * 1000));
}