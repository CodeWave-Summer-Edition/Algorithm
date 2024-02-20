select
    i1.item_id,
    item_name,
    rarity
from
    item_tree i1
    left join item_tree i2 on i1.item_id = i2.parent_item_id
    left join item_info on i1.item_id = item_info.item_id
where
    i2.parent_item_id is null
order by
    i1.item_id desc;
