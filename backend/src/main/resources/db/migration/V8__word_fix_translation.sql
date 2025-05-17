alter table
    word
alter column
    translation type varchar[]
using
    string_to_array(translation, ',');
