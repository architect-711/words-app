insert into word_groups (
    title, description, words_ids
) values (
    'All',
    'Default group',
    array(select id from word));