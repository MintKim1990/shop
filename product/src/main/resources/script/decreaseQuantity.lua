for i = 1,#KEYS do
    local quantity = redis.call('GET', KEYS[i])
    if not quantity then return 'NOTEXIST'
    elseif quantity - ARGV[i] < 0 then return 'SOLDOUT'
    end
end
for i = 1,#KEYS do
    redis.call('DECRBY', KEYS[i], ARGV[i])
end
return 'OK'